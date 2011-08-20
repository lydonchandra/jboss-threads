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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import junit.framework.Assert;
import junit.framework.TestCase;

public final class TestOrderedExecutor extends TestCase {
	
	public void testConstructor1() throws InterruptedException, ExecutionException {
		
		int maxThreadNo = 3;
		final CountDownLatch taskUnfreezer = new CountDownLatch(1);
		final CountDownLatch taskFinishLine = new CountDownLatch(maxThreadNo);

		OrderedExecutor orderedExec = new OrderedExecutor(JBossExecutors.directExecutor(),10,JBossExecutors.directExecutor()) ;

		orderedExec.executeNonBlocking(
				new Runnable() {
			@Override
			public void run() {
					System.out.println("Task 1");
					taskFinishLine.countDown();
			}			
		});
		
		orderedExec.executeNonBlocking(
			new Runnable() {
					@Override
					public void run() {
							System.out.println("Task 2");
							taskFinishLine.countDown();
					}			
				});
		
		orderedExec.executeNonBlocking(
				new Runnable() {
				@Override
				public void run() {
						System.out.println("Task 3");
						taskFinishLine.countDown();
				}			
		});
		
		Assert.assertFalse(orderedExec.isShutdown());
		Assert.assertFalse(orderedExec.isTerminated());
		
		
	}
	

}
