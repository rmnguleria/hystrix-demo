package com.rattlesnake.hystrixdemo.components;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(method = RequestMethod.GET)
public class HystrixGuard
{
	@Autowired
	private ExternalService externalService;

	@RequestMapping("/unsafe")
	String unsafe(QueryParams params)
	{
		externalService.call(params);
		return "OK";
	}

	@RequestMapping("/safe")
	String safe(QueryParams params)
	{
		return new HystrixCommand<String>(setter())
		{
			@Override
			protected String run()
			{
				externalService.call(params);
				return "OK";
			}
		}.execute();
	}

	private HystrixCommand.Setter setter()
	{
		return HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("External"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("/safe"));
	}
}
