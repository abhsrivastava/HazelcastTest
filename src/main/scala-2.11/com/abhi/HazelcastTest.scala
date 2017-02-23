package com.abhi

import com.hazelcast.config.MaxSizeConfig.MaxSizePolicy
import com.hazelcast.config._
import com.hazelcast.core.{Hazelcast, HazelcastInstance}

import scala.collection.JavaConverters._

/**
  * Created by ASrivastava on 2/23/17.
  */
object HazelcastTest {

   val instance = createInstance

   def createInstance : HazelcastInstance = {
      val config = new Config("foo")
      config.getNetworkConfig.getJoin.getTcpIpConfig.setMembers(List("localhost").asJava)
      Hazelcast.newHazelcastInstance(config)
   }

   def registerCacheKey(key: String, ttl: Int) : Unit = {
      instance.getConfig.addMapConfig(new MapConfig()
         .setName(key)
         .setTimeToLiveSeconds(ttl)
      )
   }
   def putValue(cacheKey: String, key: String, value: String) : Unit = {
      instance.getMap[String, String](cacheKey).put(key, value)
   }

   def getValue(cacheKey: String, key: String) : Option[String] = {
      Option(instance.getMap[String, String](cacheKey).get(key))
   }
}
