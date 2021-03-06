/*
 * Copyright (c) 2011, grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 *   names of its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package org.jowidgets.tutorials.tutorial4.app.common.security;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public final class AuthKeys {

	//Executor services
	public static final String EXECUTE_PERSON_LONG_LASTING = "EXECUTE_PERSON_LONG_LASTING";

	//CRUD services
	public static final String CREATE_PERSON = "CREATE_PERSON";
	public static final String READ_PERSON = "READ_PERSON";
	public static final String UPDATE_PERSON = "UPDATE_PERSON";
	public static final String DELETE_PERSON = "DELETE_PERSON";

	public static final String CREATE_ROLE = "CREATE_ROLE";
	public static final String READ_ROLE = "READ_ROLE";
	public static final String UPDATE_ROLE = "UPDATE_ROLE";
	public static final String DELETE_ROLE = "DELETE_ROLE";

	public static final String CREATE_AUTHORIZATION = "CREATE_AUTHORIZATION";
	public static final String READ_AUTHORIZATION = "READ_AUTHORIZATION";
	public static final String UPDATE_AUTHORIZATION = "UPDATE_AUTHORIZATION";
	public static final String DELETE_AUTHORIZATION = "DELETE_AUTHORIZATION";

	public static final String CREATE_PERSON_ROLE_LINK = "CREATE_PERSON_ROLE_LINK";
	public static final String DELETE_PERSON_ROLE_LINK = "DELETE_PERSON_ROLE_LINK";

	public static final String CREATE_ROLE_AUTHORIZATION_LINK = "CREATE_ROLE_AUTHORIZATION_LINK";
	public static final String DELETE_ROLE_AUTHORIZATION_LINK = "DELETE_ROLE_AUTHORIZATION_LINK";

	//Authorizations collection
	public static final Set<String> ALL_AUTHORIZATIONS = createAuthorizations();
	public static final Set<String> READ_AUTHORIZATIONS = createAuthorizations("READ_");

	private AuthKeys() {}

	private static Set<String> createAuthorizations() {
		return createAuthorizations(null);
	}

	private static Set<String> createAuthorizations(final String prefix) {
		final Set<String> result = new HashSet<String>();
		for (final Field field : AuthKeys.class.getDeclaredFields()) {
			if (prefix != null) {
				if (!field.getName().startsWith(prefix)) {
					continue;
				}
			}
			if (field.getType().equals(String.class)) {
				try {
					result.add((String) field.get(AuthKeys.class));
				}
				catch (final Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return result;
	}

}
