package com.abhi

import org.scalatest.{BeforeAndAfterAll, FunSpec}
import org.scalatest.concurrent._
import org.scalatest.time._
import org.scalatest.Matchers._
/**
  * Created by ASrivastava on 2/23/17.
  */
class MyTest extends FunSpec with BeforeAndAfterAll {
   override def beforeAll(): Unit = {
      HazelcastTest.registerCacheKey("foo", 1)
      HazelcastTest.registerCacheKey("bar", 2)
   }

   describe("should run hazelcast test") {
      it("should expire items") {
         HazelcastTest.instance.getConfig.getMapConfig("foo").getTimeToLiveSeconds should equal (1)
         HazelcastTest.putValue("foo", "1", "1")
         Thread.sleep(500)
         HazelcastTest.getValue("foo", "1") should be ('defined)
         Thread.sleep(600)
         HazelcastTest.getValue("foo", "1") should not be ('defined)
      }
      it("should not expire items") {
         HazelcastTest.putValue("bar", "1", "1")
         HazelcastTest.instance.getConfig.getMapConfig("bar").getTimeToLiveSeconds should equal (2)
         Thread.sleep(500)
         HazelcastTest.getValue("bar", "1") should be ('defined)
         Thread.sleep(600)
         HazelcastTest.getValue("bar", "1") should be ('defined)
         Thread.sleep(1100)
         HazelcastTest.getValue("bar", "1") should not be ('defined)
      }
   }
}
