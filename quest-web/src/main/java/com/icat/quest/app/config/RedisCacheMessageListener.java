package com.icat.quest.app.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class RedisCacheMessageListener implements MessageListener {
 @Override
 public void onMessage(Message message, byte[] paramArrayOfByte) {
  System.out.println( "Received by RedisMessageListener: " + message.toString() );
 }
}