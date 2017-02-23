package com.abhi

import org.scalatest.{BeforeAndAfterAll, FunSpec}
import org.scalatest.concurrent._
import org.scalatest.time._
import org.scalatest.Matchers._
/**
  * Created by ASrivastava on 2/23/17.
  */
class MyTest extends FunSpec {

   describe("should run hazelcast test") {
      it("should expire items") {
         HazelcastTest.registerCacheKey("foo.1", 1)
         HazelcastTest.instance.getConfig.getMapConfig("foo.1").getTimeToLiveSeconds should equal (1)
         HazelcastTest.putValue("foo.1", "1", "1")
         Thread.sleep(500)
         HazelcastTest.getValue("foo.1", "1") should be ('defined)
         Thread.sleep(600)
         HazelcastTest.getValue("foo.1", "1") should not be ('defined)
      }
      it("should not expire items") {
         HazelcastTest.registerCacheKey("foo.2", 2)
         HazelcastTest.putValue("foo.2", "1", "1")
         HazelcastTest.instance.getConfig.getMapConfig("foo.2").getTimeToLiveSeconds should equal (2)
         Thread.sleep(500)
         HazelcastTest.getValue("foo.2", "1") should be ('defined)
         Thread.sleep(600)
         HazelcastTest.getValue("foo.2", "1") should be ('defined)
         Thread.sleep(1100)
         HazelcastTest.getValue("foo.2", "1") should not be ('defined)
      }
   }
}
