package com.rattlesnake.hystrixdemo.components;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.*;

/**
 * Timeouts for unsafe code
 * - Limit max duration
 * - You always pay the price of slow query / fail fast
 * - Circuit breaking
 * - Missing metrics: avg time, queue length
 */
public class Timeouts
{

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException
	{

		ExecutorService pool = Executors.newFixedThreadPool(10);

		Future<String> future = pool.submit(Timeouts::call);

		System.out.println(future.get(1, TimeUnit.SECONDS));

		pool.shutdownNow();
	}

	private static String call() throws IOException
	{
		URL url = new URL("http://example.com");

		InputStream inputStream = null;

		try
		{
			inputStream = url.openStream();
			return IOUtils.toString(inputStream, "UTF-8");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (inputStream != null)
			{
				inputStream.close();
			}
		}
		return "Null";
	}
}
