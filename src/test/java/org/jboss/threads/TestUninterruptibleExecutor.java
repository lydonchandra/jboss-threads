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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import junit.framework.Assert;
import junit.framework.TestCase;

public final class TestUninterruptibleExecutor extends TestCase {
	
	public void testConstructor() throws InterruptedException, ExecutionException {
		
		UninterruptibleExecutor uninterruptExec = new UninterruptibleExecutor(Executors.newFixedThreadPool(1));

		// task to be interrupted
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("starting uninterruptible task 1");
					Thread.sleep(3000);
					System.out.println("stopping uninterruptible task 1");
				} catch (InterruptedException e) {
					assertFalse("This line should never be reached.", true);
					e.printStackTrace();
				}				
			}			
		};
		final Thread t1 = new Thread(runnable);
		
		uninterruptExec.execute(t1);
		
	}
	

}
