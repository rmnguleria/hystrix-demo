package com.rattlesnake.hystrixdemo.components;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Unsafe code:
 * - broken URL
 * - slow DNS
 * - broken SSL certificate
 * - slow network connection
 * - server replying slowly or never
 */
public class Introduction
{

	public static void main(String[] args) throws IOException
	{
		URL url = new URL("http://example.com");

		InputStream inputStream = null;

		try
		{
			inputStream = url.openStream();
			System.out.println(IOUtils.toString(inputStream, "UTF-8"));
		}
		finally
		{
			if (inputStream != null)
			{
				inputStream.close();
			}
		}

	}

}
