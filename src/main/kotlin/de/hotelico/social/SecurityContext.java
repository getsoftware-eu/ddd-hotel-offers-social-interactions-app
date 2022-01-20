/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.hotelico.social;

import de.hotelico.model.Customer;

/**
 * Simple SecurityContext that stores the currently signed-in connection in a thread local.
 * @author Keith Donald
 */
public final class SecurityContext {

	private static final ThreadLocal<Customer> currentCustomer = new ThreadLocal<Customer>();

	public static Customer getCurrentCustomer() {
		Customer user = currentCustomer.get();
		if (user == null) {
			throw new IllegalStateException("No user is currently signed in");
		}
		return user;
	}

	public static void setCurrentCustomer(Customer user) {
		currentCustomer.set(user);
	}

	public static boolean userSignedIn() {
		return currentCustomer.get() != null;
	}

	public static void remove() {
		currentCustomer.remove();
	}

}
