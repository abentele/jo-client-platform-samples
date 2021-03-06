/*
 * Copyright (c) 2016, bentele
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
package org.jowidgets.samples.kitchensink.sample2.app.service.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jowidgets.cap.common.api.CapCommonToolkit;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.common.api.lookup.ILookUpEntry;
import org.jowidgets.cap.common.api.lookup.ILookUpEntryBuilder;
import org.jowidgets.cap.common.api.lookup.ILookUpToolkit;
import org.jowidgets.cap.service.api.adapter.ISyncLookUpService;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.IGenericProperty;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.genericproperties.GenericProperty;
import org.jowidgets.samples.kitchensink.sample2.app.service.entity.EntityManagerProvider;

public class GenericPropertyLookupService implements ISyncLookUpService {

	@Override
	public List<ILookUpEntry> readValues(final IExecutionCallback executionCallback) {

		final ILookUpToolkit lookUpToolkit = CapCommonToolkit.lookUpToolkit();

		final EntityManager entityManager = EntityManagerProvider.get();

		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<GenericProperty> criteriaQuery = criteriaBuilder.createQuery(GenericProperty.class);
		final Root<GenericProperty> root = criteriaQuery.from(GenericProperty.class);
		criteriaQuery.orderBy(
				criteriaBuilder.asc(root.get(IGenericProperty.SORT_INDEX_PROPERTY)),
				criteriaBuilder.asc(root.get(IGenericProperty.LABEL_PROPERTY)));

		final List<GenericProperty> resultList = entityManager.createQuery(criteriaQuery).getResultList();

		final List<ILookUpEntry> result = new ArrayList<ILookUpEntry>();
		result.add(lookUpToolkit.lookUpEntry(null, ""));
		for (final GenericProperty genericProperty : resultList) {
			final ILookUpEntryBuilder entryBuilder = lookUpToolkit.lookUpEntryBuilder();
			entryBuilder.setKey(genericProperty.getPropertyName());
			entryBuilder.setValue(genericProperty.getLabel());
			entryBuilder.setDescription(genericProperty.getDescription());
			entryBuilder.setValue(IGenericProperty.TYPE_PROPERTY, genericProperty.getType());
			entryBuilder.setValue(IGenericProperty.ENTITY_PROPERTY, genericProperty.getEntity());
			entryBuilder.setValid(true);
			result.add(entryBuilder.build());
		}

		return Collections.unmodifiableList(result);
	}

}
