/*
 * Copyright (c) 2013, grossmann
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
package org.jowidgets.tutorials.tutorial4.app.common.bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jowidgets.cap.common.api.annotation.PropertyValidator;
import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.security.common.api.annotation.CreateAuthorization;
import org.jowidgets.cap.security.common.api.annotation.DeleteAuthorization;
import org.jowidgets.cap.security.common.api.annotation.ReadAuthorization;
import org.jowidgets.cap.security.common.api.annotation.UpdateAuthorization;
import org.jowidgets.tutorials.tutorial4.app.common.dto.Gender;
import org.jowidgets.tutorials.tutorial4.app.common.security.AuthKeys;
import org.jowidgets.tutorials.tutorial4.app.common.validation.PersonNameValidator;

@CreateAuthorization(AuthKeys.CREATE_PERSON)
@ReadAuthorization(AuthKeys.READ_PERSON)
@UpdateAuthorization(AuthKeys.UPDATE_PERSON)
@DeleteAuthorization(AuthKeys.DELETE_PERSON)
public interface IPerson extends IBean {

	String NAME_PROPERTY = "name";
	String DATE_OF_BIRTH_PROPERTY = "dateOfBirth";
	String GENDER_PROPERTY = "gender";

	List<String> ALL_PROPERTIES = Arrays.asList(
			NAME_PROPERTY,
			DATE_OF_BIRTH_PROPERTY,
			GENDER_PROPERTY,
			IBean.ID_PROPERTY,
			IBean.VERSION_PROPERTY);

	@NotNull
	@Size(min = 2, max = 50)
	@PropertyValidator(PersonNameValidator.class)
	String getName();

	void setName(String loginName);

	Date getDateOfBirth();

	void setDateOfBirth(Date date);

	Gender getGender();

	void setGender(Gender gender);

}
