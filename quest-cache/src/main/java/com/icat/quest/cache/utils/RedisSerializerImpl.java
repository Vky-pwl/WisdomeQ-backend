package com.icat.quest.cache.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
@Service
public class RedisSerializerImpl  implements RedisSerializer<Object>
{
	
	
	public byte[] serialize(Object obj) 
	{
       		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		    ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(byteOut);
				out.writeObject(obj);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			return byteOut.toByteArray();
		    
	}
	
	
	public Object deserialize(byte[] data) 
	{
		
		    ObjectInputStream in;
			try 
			{		
						
				ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
				 
				
				in = new ObjectInputStream(byteIn);
				Object ob= in.readObject();
				
				return ob;
			} 
			catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (NullPointerException  e ) 
			{
				
			}
			return null;	   
	}
}	
