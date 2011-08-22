/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import junit.framework.TestCase;

public final class TestTimeoutThreadPoolExecutor extends TestCase {
	
	public void testConstructor() {
		BlockingQueue<Runnable> blockingQ = new ArrayBlockingQueue<Runnable>(100);
		blockingQ.add(new Runnable() {
			@Override
			public void run() {
				System.out.println("Running inside TimeoutThreadPoolExecutor");
			}
		});
		
		TimeoutThreadPoolExecutor timeoutExec = new TimeoutThreadPoolExecutor(
		2, 2, 1, TimeUnit.SECONDS, blockingQ , 1, TimeUnit.SECONDS);		
	}
	
	//public void testTimeout() {
	public static void main(String [] args) throws InterruptedException {
		TimeoutThreadPoolExecutor timeoutExec = new TimeoutThreadPoolExecutor(
			2, 2, // pool size 
			1, TimeUnit.SECONDS, // keep alive time 
			new ArrayBlockingQueue<Runnable>(100) , 
			1, TimeUnit.SECONDS // timeout time
		);
		
		timeoutExec.execute(new Runnable() {
			@Override
			public void run() {				
				System.out.println("Timing out inside TimeoutThreadPoolExecutor");
				try {
					Thread.sleep(3000);
					System.out.println("Why didn't I get interrupted ?");
				} catch (InterruptedException e) {
					System.out.println("Timed out!");
					assertTrue("We should get InterruptedException because the thread exceeds 1 second", true);				
				}
			}
		});
		
		Thread.sleep(5000);
		timeoutExec.shutdown();
	}
	

}
