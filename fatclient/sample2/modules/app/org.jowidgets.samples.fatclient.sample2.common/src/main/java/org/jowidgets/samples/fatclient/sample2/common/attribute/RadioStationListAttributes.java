/*
 * Copyright (c) 2014, grossmann
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

package org.jowidgets.samples.fatclient.sample2.common.attribute;

import java.util.List;

import org.jowidgets.cap.ui.api.attribute.Attributes;
import org.jowidgets.cap.ui.api.attribute.IAttribute;
import org.jowidgets.cap.ui.api.attribute.IBeanAttributeBluePrint;
import org.jowidgets.cap.ui.api.attribute.IBeanAttributesBuilder;
import org.jowidgets.samples.fatclient.sample2.common.bean.Category;
import org.jowidgets.samples.fatclient.sample2.common.bean.RadioStation;

public final class RadioStationListAttributes {

	public static final List<IAttribute<Object>> INSTANCE = createInstance();

	private RadioStationListAttributes() {}

	private static List<IAttribute<Object>> createInstance() {
		final IBeanAttributesBuilder builder = Attributes.builder(RadioStation.class);

		//name
		IBeanAttributeBluePrint<Object> bp = builder.add(RadioStation.NAME_PROPERTY).setLabel("Name");
		bp.setTableColumnWidth(180);

		//category
		bp = builder.add(RadioStation.CATEGORY_PROPERTY).setLabel("Category");
		bp.setTableColumnWidth(100);
		bp.setDefaultValue(Category.OTHER);

		return builder.build();
	};

}
