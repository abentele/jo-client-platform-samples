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

package org.jowidgets.examples.rcp3.example7.component;

import org.jowidgets.addons.icons.silkicons.SilkIcons;
import org.jowidgets.api.command.IAction;
import org.jowidgets.api.model.item.IMenuModel;
import org.jowidgets.api.model.item.IToolBarModel;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IPopupMenu;
import org.jowidgets.api.widgets.ITable;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Position;
import org.jowidgets.common.widgets.controller.IPopupDetectionListener;
import org.jowidgets.common.widgets.controller.ITableCellPopupDetectionListener;
import org.jowidgets.common.widgets.controller.ITableCellPopupEvent;
import org.jowidgets.examples.rcp3.example7.command.CreateRoleActionFactory;
import org.jowidgets.examples.rcp3.example7.command.DeleteRoleActionFactory;
import org.jowidgets.examples.rcp3.example7.command.EditRoleActionFactory;
import org.jowidgets.examples.rcp3.example7.model.BeanTableModel;
import org.jowidgets.examples.rcp3.example7.model.Role;
import org.jowidgets.tools.layout.MigLayoutFactory;
import org.jowidgets.tools.widgets.blueprint.BPF;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.tools.AbstractView;

public final class RoleTableView extends AbstractView {

	public static final String ID = RoleTableView.class.getName();
	public static final String DEFAULT_LABEL = "Roles";
	public static final String DEFAULT_TOOLTIP = "Shows all roles";
	public static final IImageConstant DEFAULT_ICON = SilkIcons.GROUP;

	public RoleTableView(final IViewContext context, final BeanTableModel<Role> model) {
		final IContainer container = context.getContainer();
		container.setLayout(MigLayoutFactory.growingInnerCellLayout());

		final ITable table = container.add(BPF.table(model), MigLayoutFactory.GROWING_CELL_CONSTRAINTS);
		table.pack();

		final IAction createAction = CreateRoleActionFactory.create(model);
		final IAction editAction = EditRoleActionFactory.create(model);
		final IAction deleteAction = DeleteRoleActionFactory.create(model);

		final IToolBarModel toolBar = context.getToolBar();
		toolBar.addAction(createAction);
		toolBar.addAction(editAction);
		toolBar.addAction(deleteAction);

		final IPopupMenu tableMenu = table.createPopupMenu();
		final IMenuModel tableMenuModel = tableMenu.getModel();
		tableMenuModel.addAction(createAction);

		table.addPopupDetectionListener(new IPopupDetectionListener() {
			@Override
			public void popupDetected(final Position position) {
				tableMenu.show(position);
			}
		});

		final IPopupMenu tableCellMenu = table.createPopupMenu();
		final IMenuModel tableCellMenuModel = tableCellMenu.getModel();
		tableCellMenuModel.addAction(createAction);
		tableCellMenuModel.addAction(editAction);
		tableCellMenuModel.addAction(deleteAction);

		table.addTableCellPopupDetectionListener(new ITableCellPopupDetectionListener() {
			@Override
			public void popupDetected(final ITableCellPopupEvent event) {
				tableCellMenu.show(event.getPosition());
			}
		});
	}

}
