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

package org.jowidgets.samples.kitchensink.sample2.app.service.entity;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.bean.IBeanKey;
import org.jowidgets.cap.common.api.filter.ArithmeticFilter;
import org.jowidgets.cap.common.api.filter.ArithmeticOperator;
import org.jowidgets.cap.common.api.filter.IFilter;
import org.jowidgets.cap.common.api.service.ICreatorService;
import org.jowidgets.cap.common.api.service.IDeleterService;
import org.jowidgets.cap.common.api.service.IReaderService;
import org.jowidgets.cap.common.api.service.IUpdaterService;
import org.jowidgets.cap.service.api.creator.ICreatorServiceBuilder;
import org.jowidgets.cap.service.api.entity.IBeanEntityBluePrint;
import org.jowidgets.cap.service.api.entity.IBeanEntityLinkBluePrint;
import org.jowidgets.cap.service.api.updater.IUpdaterServiceBuilder;
import org.jowidgets.cap.service.jpa.api.query.ICriteriaQueryCreatorBuilder;
import org.jowidgets.cap.service.jpa.api.query.IPredicateCreator;
import org.jowidgets.cap.service.jpa.api.query.JpaQueryToolkit;
import org.jowidgets.cap.service.jpa.tools.entity.JpaEntityServiceBuilderWrapper;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.IAuthorization;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.ICategory;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.IPerson;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.IPersonPersonLink;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.IPersonRelationType;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.IPersonRoleLink;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.IPhone;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.IRole;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.IRoleAuthorizationLink;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.genericproperties.IGenericProperty;
import org.jowidgets.samples.kitchensink.sample2.app.common.entity.EntityIds;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Authorization;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Category;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Country;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Person;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.PersonPersonLink;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.PersonRelationType;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.PersonRoleLink;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Phone;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.Role;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.RoleAuthorizationLink;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.RoleCategoryLink;
import org.jowidgets.samples.kitchensink.sample2.app.service.bean.genericproperties.GenericProperty;
import org.jowidgets.samples.kitchensink.sample2.app.service.descriptor.AuthorizationDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.service.descriptor.CategoryDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.service.descriptor.CountryDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.service.descriptor.GenericPropertyDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.service.descriptor.PersonDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.service.descriptor.PersonOfSourcePersonLinkDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.service.descriptor.PersonPersonLinkDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.service.descriptor.PersonRelationTypeDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.service.descriptor.PhoneDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.service.descriptor.RoleDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.service.descriptor.SourcePersonOfPersonLinkDtoDescriptorBuilder;
import org.jowidgets.samples.kitchensink.sample2.app.service.loader.PersonRelationTypeLoader;
import org.jowidgets.service.api.IServiceRegistry;

public class SampleEntityServiceBuilder extends JpaEntityServiceBuilderWrapper {

	private final List<PersonRelationType> personRelationTypes;

	public SampleEntityServiceBuilder(final IServiceRegistry registry) {
		super(registry);

		this.personRelationTypes = PersonRelationTypeLoader.load();

		//IPerson
		IBeanEntityBluePrint entityBp = addEntity().setEntityId(EntityIds.PERSON).setBeanType(Person.class);
		entityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder());
		entityBp.setCreatorService(createPersonCreatorService());
		entityBp.setUpdaterService(createPersonUpdaterService());
		addPersonLinkDescriptors(entityBp);

		//IRole
		entityBp = addEntity().setEntityId(EntityIds.ROLE).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		addRoleLinkDescriptors(entityBp);

		//IAuthorization
		entityBp = addEntity().setEntityId(EntityIds.AUTHORIZATION).setBeanType(Authorization.class);
		entityBp.setDtoDescriptor(new AuthorizationDtoDescriptorBuilder());
		addAuthorizationRoleLinkDescriptor(entityBp);

		//IPersonLinkType
		entityBp = addEntity().setEntityId(EntityIds.PERSON_LINK_TYPE).setBeanType(PersonRelationType.class);
		entityBp.setDtoDescriptor(new PersonRelationTypeDtoDescriptorBuilder());

		//ICountry
		entityBp = addEntity().setEntityId(EntityIds.COUNTRY).setBeanType(Country.class);
		entityBp.setDtoDescriptor(new CountryDtoDescriptorBuilder());

		//IPhone
		entityBp = addEntity().setEntityId(EntityIds.PHONE).setBeanType(Phone.class);
		entityBp.setDtoDescriptor(new PhoneDtoDescriptorBuilder());
		entityBp.setProperties(IPhone.ALL_PROPERTIES);
		addPersonsOfPhonesLinkDescriptor(entityBp);

		//ICategory
		entityBp = addEntity().setEntityId(EntityIds.CATEGORY).setBeanType(Category.class);
		entityBp.setDtoDescriptor(new CategoryDtoDescriptorBuilder());
		entityBp.setProperties(ICategory.ALL_PROPERTIES);
		addCategoryLinkDescriptors(entityBp);

		//IGenericProperty
		entityBp = addEntity().setEntityId(EntityIds.GENERIC_PROPERTY).setBeanType(GenericProperty.class);
		entityBp.setDtoDescriptor(new GenericPropertyDtoDescriptorBuilder());
		entityBp.setProperties(IGenericProperty.ALL_PROPERTIES);
		addCategoryLinkDescriptors(entityBp);

		//IPersonsOfSourcePersonLink
		entityBp = addEntity().setEntityId(EntityIds.PERSONS_OF_SOURCE_PERSONS_LINK).setBeanType(PersonPersonLink.class);
		entityBp.setDtoDescriptor(new PersonOfSourcePersonLinkDtoDescriptorBuilder());

		//SourcePersonOfPersonLink
		entityBp = addEntity().setEntityId(EntityIds.SOURCE_PERSONS_OF_PERSONS_LINK).setBeanType(PersonPersonLink.class);
		entityBp.setDtoDescriptor(new SourcePersonOfPersonLinkDtoDescriptorBuilder());

		//Linked persons of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKED_PERSONS_OF_ROLES).setBeanType(Person.class);
		entityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder());
		entityBp.setReaderService(createPersonsOfRolesReader(true));
		entityBp.setUpdaterService(createPersonUpdaterService());
		addPersonLinkDescriptors(entityBp);

		//Linkable persons of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_PERSONS_OF_ROLES).setBeanType(Person.class);
		entityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder());
		entityBp.setReaderService(createPersonsOfRolesReader(false));
		entityBp.setCreatorService(createPersonCreatorService());

		//Linked roles of persons
		entityBp = addEntity().setEntityId(EntityIds.LINKED_ROLES_OF_PERSONS).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		entityBp.setReaderService(createRolesOfPersonsReader(true));
		entityBp.setDeleterService((IDeleterService) null);
		addRoleLinkDescriptors(entityBp);

		//Linkable roles of persons
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_ROLES_OF_PERSONS).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		entityBp.setReaderService(createRolesOfPersonsReader(false));
		entityBp.setDeleterService((IDeleterService) null);

		//Linked roles of authorizations
		entityBp = addEntity().setEntityId(EntityIds.LINKED_ROLES_OF_AUTHORIZATIONS).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		entityBp.setReaderService(createRolesOfAuthorizationsReader(true));
		entityBp.setDeleterService((IDeleterService) null);
		addRoleLinkDescriptors(entityBp);

		//Linkable roles of authorizations
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_ROLES_OF_AUTHORIZATIONS).setBeanType(Role.class);
		entityBp.setDtoDescriptor(new RoleDtoDescriptorBuilder());
		entityBp.setReaderService(createRolesOfAuthorizationsReader(false));
		entityBp.setDeleterService((IDeleterService) null);

		//Linked authorizations of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKED_AUTHORIZATION_OF_ROLES).setBeanType(Authorization.class);
		entityBp.setDtoDescriptor(new AuthorizationDtoDescriptorBuilder());
		entityBp.setReaderService(createAuthorizationsOfRolesReader(true));
		entityBp.setDeleterService((IDeleterService) null);
		addAuthorizationRoleLinkDescriptor(entityBp);

		//Linkable authorizations of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_AUTHORIZATIONS_OF_ROLES).setBeanType(Authorization.class);
		entityBp.setDtoDescriptor(new AuthorizationDtoDescriptorBuilder());
		entityBp.setReaderService(createAuthorizationsOfRolesReader(false));
		entityBp.setDeleterService((IDeleterService) null);

		//Linked categories of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKED_CATEGORIES_OF_ROLES).setBeanType(Category.class);
		entityBp.setDtoDescriptor(new CategoryDtoDescriptorBuilder());
		entityBp.setReaderService(createCategoriesOfRolesReader(true));
		entityBp.setDeleterService((IDeleterService) null);
		addCategoryLinkDescriptors(entityBp);

		//Linkable categories of roles
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_CATEGORIES_OF_ROLES).setBeanType(Category.class);
		entityBp.setDtoDescriptor(new CategoryDtoDescriptorBuilder());
		entityBp.setReaderService(createCategoriesOfRolesReader(false));
		entityBp.setDeleterService((IDeleterService) null);

		// Linked phones of persons
		entityBp = addEntity().setEntityId(EntityIds.LINKED_PHONES_OF_PERSONS).setBeanType(Phone.class);
		entityBp.setProperties(IPhone.ALL_PROPERTIES);
		entityBp.setDtoDescriptor(new PhoneDtoDescriptorBuilder());
		entityBp.setCreatorService((ICreatorService) null);
		entityBp.setReaderService(createPhonesOfPersonLinkReader(true));
		entityBp.setProperties(IPhone.ALL_PROPERTIES);
		addPersonsOfPhonesLinkDescriptor(entityBp);

		// Linkable phones of persons
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_PHONES_OF_PERSONS).setBeanType(Phone.class);
		entityBp.setProperties(IPhone.ALL_PROPERTIES);
		entityBp.setDtoDescriptor(new PhoneDtoDescriptorBuilder());
		entityBp.setCreatorService((ICreatorService) null);
		entityBp.setDeleterService((IDeleterService) null);
		entityBp.setReaderService(createPhonesOfPersonLinkReader(false));

		// Linked person of phones
		entityBp = addEntity().setEntityId(EntityIds.LINKED_PERSON_OF_PHONES).setBeanType(Person.class);
		entityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder("User", "User"));
		entityBp.setCreatorService((ICreatorService) null);
		entityBp.setReaderService(createPersonsOfPhonesLinkReader(true));
		addPersonLinkDescriptors(entityBp);

		// Linkable persons of phones
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_PERSONS_OF_PHONES).setBeanType(Person.class);
		entityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder());
		entityBp.setCreatorService((ICreatorService) null);
		entityBp.setDeleterService((IDeleterService) null);
		entityBp.setReaderService(createPersonsOfPhonesLinkReader(false));

		// Linked sub categories of categories
		entityBp = addEntity().setEntityId(EntityIds.LINKED_SUB_CATEGORIES_OF_CATEGORIES).setBeanType(Category.class);
		entityBp.setDtoDescriptor(new CategoryDtoDescriptorBuilder("Sub category", "Sub categories"));
		entityBp.setCreatorService((ICreatorService) null);
		entityBp.setReaderService(createSubCategoriesOfCategoriesLinkReader(true));
		entityBp.setProperties(ICategory.ALL_PROPERTIES);
		addCategoryLinkDescriptors(entityBp);

		// Linkable sub categories of categories
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_SUB_CATEGORIES_OF_CATEGORIES).setBeanType(Category.class);
		entityBp.setDtoDescriptor(new CategoryDtoDescriptorBuilder());
		entityBp.setCreatorService((ICreatorService) null);
		entityBp.setDeleterService((IDeleterService) null);
		entityBp.setProperties(ICategory.ALL_PROPERTIES);
		entityBp.setReaderService(createSubCategoriesOfCategoriesLinkReader(false));

		// Linked super category of categories
		entityBp = addEntity().setEntityId(EntityIds.LINKED_SUPER_CATEGORY_OF_CATEGORIES).setBeanType(Category.class);
		entityBp.setDtoDescriptor(new CategoryDtoDescriptorBuilder("Super category", "Super category"));
		entityBp.setCreatorService((ICreatorService) null);
		entityBp.setReaderService(createSuperCategoryOfCategoriesLinkReader(true));
		entityBp.setProperties(ICategory.ALL_PROPERTIES);
		addCategoryLinkDescriptors(entityBp);

		// Linkable super category of categories
		entityBp = addEntity().setEntityId(EntityIds.LINKABLE_SUPER_CATEGORY_OF_CATEGORIES).setBeanType(Category.class);
		entityBp.setDtoDescriptor(new CategoryDtoDescriptorBuilder());
		entityBp.setCreatorService((ICreatorService) null);
		entityBp.setDeleterService((IDeleterService) null);
		entityBp.setProperties(ICategory.ALL_PROPERTIES);
		entityBp.setReaderService(createSuperCategoryOfCategoriesLinkReader(false));

	}

	private void addPersonLinkDescriptors(final IBeanEntityBluePrint entityBp) {
		addPersonLinkDescriptors(entityBp, true);
	}

	private void addPersonLinkDescriptors(final IBeanEntityBluePrint entityBp, final boolean createEntities) {
		addPersonRoleLinkDescriptor(entityBp);
		for (final IPersonRelationType personRelationType : personRelationTypes) {
			addPersonPersonLinkDescriptor(
					entityBp,
					createEntities,
					false,
					personRelationType.getId(),
					personRelationType.getReverseRelationName(),
					personRelationType.getReverseRelationName());

			addPersonPersonLinkDescriptor(
					entityBp,
					createEntities,
					true,
					personRelationType.getId(),
					personRelationType.getRelationName(),
					personRelationType.getRelationName());
		}
		addPhonesofPersonsLinkDescriptor(entityBp);
	}

	private void addRoleLinkDescriptors(final IBeanEntityBluePrint entityBp) {
		addRolePersonLinkDescriptor(entityBp);
		addRoleAuthorizationLinkDescriptor(entityBp);
		addRoleCategoryLinkDescriptor(entityBp);
	}

	private void addCategoryLinkDescriptors(final IBeanEntityBluePrint entityBp) {
		addCategorySubCategoryLinkDescriptor(entityBp);
		addCategorySuperCategoryLinkDescriptor(entityBp);
	}

	private void addPersonRoleLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.PERSON_ROLE_LINK);
		bp.setLinkBeanType(PersonRoleLink.class);
		bp.setLinkedEntityId(EntityIds.LINKED_ROLES_OF_PERSONS);
		bp.setLinkableEntityId(EntityIds.LINKABLE_ROLES_OF_PERSONS);
		bp.setSourceProperties(IPersonRoleLink.PERSON_ID_PROPERTY);
		bp.setDestinationProperties(IPersonRoleLink.ROLE_ID_PROPERTY);
	}

	private void addPhonesofPersonsLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.PHONE);
		bp.setLinkedEntityId(EntityIds.LINKED_PHONES_OF_PERSONS);
		bp.setLinkableEntityId(EntityIds.LINKABLE_PHONES_OF_PERSONS);
		bp.setSourceProperties(Phone.PERSON_ID_PROPERTY);
	}

	private void addPersonsOfPhonesLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.PHONE);
		bp.setLinkedEntityId(EntityIds.LINKED_PERSON_OF_PHONES);
		bp.setLinkableEntityId(EntityIds.LINKABLE_PERSONS_OF_PHONES);
		bp.setDestinationProperties(Phone.PERSON_ID_PROPERTY);
	}

	private void addCategorySubCategoryLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.CATEGORY);
		bp.setLinkBeanType(Category.class);
		bp.setLinkedEntityId(EntityIds.LINKED_SUB_CATEGORIES_OF_CATEGORIES);
		bp.setLinkableEntityId(EntityIds.LINKABLE_SUB_CATEGORIES_OF_CATEGORIES);
		bp.setSourceProperties(ICategory.SUPER_CATEGORY_ID_PROPERTY);
	}

	private void addCategorySuperCategoryLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.CATEGORY);
		bp.setLinkBeanType(Category.class);
		bp.setLinkedEntityId(EntityIds.LINKED_SUPER_CATEGORY_OF_CATEGORIES);
		bp.setLinkableEntityId(EntityIds.LINKABLE_SUPER_CATEGORY_OF_CATEGORIES);
		bp.setDestinationProperties(ICategory.SUPER_CATEGORY_ID_PROPERTY);
	}

	private void addPersonPersonLinkDescriptor(
		final IBeanEntityBluePrint entityBp,
		final boolean createEntities,
		final boolean reverse,
		final Object relationTypeId,
		final String labelSingular,
		final String labelPlural) {
		final IBeanEntityLinkBluePrint link = entityBp.addLink();

		final String linkEntityId = "PERSON_PERSON_LINK_" + relationTypeId + "_" + reverse;
		final String linkedEntityId = "PERSON_PERSON_LINKED_" + relationTypeId + "_" + reverse;
		final String linkableEntityId = "PERSON_PERSON_LINKABLE_" + relationTypeId + "_" + reverse;

		link.setLinkEntityId(linkEntityId);
		link.setLinkBeanType(PersonPersonLink.class);
		link.setLinkedEntityId(linkedEntityId);
		link.setLinkableEntityId(linkableEntityId);

		if (reverse) {
			link.setSourceProperties(IPersonPersonLink.DESTINATION_PERSON_ID_PROPERTY);
			link.setDestinationProperties(IPersonPersonLink.SOURCE_PERSON_ID_PROPERTY);
		}
		else {
			link.setSourceProperties(IPersonPersonLink.SOURCE_PERSON_ID_PROPERTY);
			link.setDestinationProperties(IPersonPersonLink.DESTINATION_PERSON_ID_PROPERTY);
		}

		if (createEntities) {

			final String personPersonLinkAttribute;
			final String personAttribute;
			if (reverse) {
				personPersonLinkAttribute = "sourcePersonOfPersonLinks";
				personAttribute = "destinationPerson";
			}
			else {
				personPersonLinkAttribute = "personOfSourcePersonLinks";
				personAttribute = "sourcePerson";
			}

			final IBeanEntityBluePrint linkEntityBp = addEntity();
			linkEntityBp.setEntityId(linkEntityId).setBeanType(PersonPersonLink.class);
			linkEntityBp.setDtoDescriptor(new PersonPersonLinkDtoDescriptorBuilder(
				relationTypeId,
				labelSingular + " link",
				labelPlural + " links"));

			final IPredicateCreator<Void> relationTypePredicateCreator = new IPredicateCreator<Void>() {
				@Override
				public Predicate createPredicate(
					final CriteriaBuilder criteriaBuilder,
					final Root<?> bean,
					final CriteriaQuery<?> query,
					final List<IBeanKey> parentBeanKeys,
					final List<Object> parentBeanIds,
					final Void parameter) {
					final Join<Object, Object> linkPath = bean.join(personPersonLinkAttribute);
					final Path<Object> relationTypePath = linkPath.get("relationType").get(IBean.ID_PROPERTY);
					return relationTypePath.in(relationTypeId);
				}
			};

			final ICriteriaQueryCreatorBuilder<Void> linkedQueryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Person.class);
			linkedQueryBuilder.setParentPropertyPath(true, personPersonLinkAttribute, personAttribute);
			linkedQueryBuilder.addPredicateCreator(relationTypePredicateCreator);
			final IReaderService<Void> linkedReader = getServiceFactory().readerService(
					Person.class,
					linkedQueryBuilder.build(),
					IPerson.ALL_PROPERTIES);

			final IBeanEntityBluePrint linkedEntityBp = addEntity().setEntityId(linkedEntityId).setBeanType(Person.class);
			linkedEntityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder(labelSingular, labelPlural));
			linkedEntityBp.setReaderService(linkedReader);
			linkedEntityBp.setUpdaterService(createPersonUpdaterService());
			linkedEntityBp.setCreatorService((ICreatorService) null);
			addPersonLinkDescriptors(linkedEntityBp, false);

			final ICriteriaQueryCreatorBuilder<Void> linakbleQueryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Person.class);
			linakbleQueryBuilder.setParentPropertyPath(false, personPersonLinkAttribute, personAttribute);
			linkedQueryBuilder.addPredicateCreator(relationTypePredicateCreator);
			final IReaderService<Void> linkableReader = getServiceFactory().readerService(
					Person.class,
					linakbleQueryBuilder.build(),
					IPerson.ALL_PROPERTIES);

			final IBeanEntityBluePrint linkableEntityBp = addEntity().setEntityId(linkableEntityId).setBeanType(Person.class);
			linkableEntityBp.setReaderService(linkableReader);
			linkableEntityBp.setDtoDescriptor(new PersonDtoDescriptorBuilder());
			linkableEntityBp.setCreatorService(createPersonCreatorService());
		}
	}

	private void addRolePersonLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.PERSON_ROLE_LINK);
		bp.setLinkBeanType(PersonRoleLink.class);
		bp.setLinkedEntityId(EntityIds.LINKED_PERSONS_OF_ROLES);
		bp.setLinkableEntityId(EntityIds.LINKABLE_PERSONS_OF_ROLES);
		bp.setSourceProperties(IPersonRoleLink.ROLE_ID_PROPERTY);
		bp.setDestinationProperties(IPersonRoleLink.PERSON_ID_PROPERTY);
	}

	private void addRoleAuthorizationLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.ROLE_AUTHORIZATION_LINK);
		bp.setLinkBeanType(RoleAuthorizationLink.class);
		bp.setLinkedEntityId(EntityIds.LINKED_AUTHORIZATION_OF_ROLES);
		bp.setLinkableEntityId(EntityIds.LINKABLE_AUTHORIZATIONS_OF_ROLES);
		bp.setSourceProperties(IRoleAuthorizationLink.ROLE_ID_PROPERTY);
		bp.setDestinationProperties(IRoleAuthorizationLink.AUTHORIZATION_ID_PROPERTY);
	}

	private void addRoleCategoryLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkBeanType(RoleCategoryLink.class);
		bp.setLinkedEntityId(EntityIds.LINKED_CATEGORIES_OF_ROLES);
		bp.setLinkableEntityId(EntityIds.LINKABLE_CATEGORIES_OF_ROLES);
		bp.setSourceProperties(RoleCategoryLink.ROLE_ID_PROPERTY);
		bp.setDestinationProperties(RoleCategoryLink.CATEGORY_ID_PROPERTY);
	}

	private void addAuthorizationRoleLinkDescriptor(final IBeanEntityBluePrint entityBp) {
		final IBeanEntityLinkBluePrint bp = entityBp.addLink();
		bp.setLinkEntityId(EntityIds.ROLE_AUTHORIZATION_LINK);
		bp.setLinkBeanType(RoleAuthorizationLink.class);
		bp.setLinkedEntityId(EntityIds.LINKED_ROLES_OF_AUTHORIZATIONS);
		bp.setLinkableEntityId(EntityIds.LINKABLE_ROLES_OF_AUTHORIZATIONS);
		bp.setSourceProperties(IRoleAuthorizationLink.AUTHORIZATION_ID_PROPERTY);
		bp.setDestinationProperties(IRoleAuthorizationLink.ROLE_ID_PROPERTY);
	}

	private IReaderService<Void> createPersonsOfRolesReader(final boolean linked) {
		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Person.class);
		queryBuilder.setParentPropertyPath(linked, "personRoleLinks", "role");
		if (!linked) {
			final IFilter filter = ArithmeticFilter.create(IPerson.ACTIVE_PROPERTY, ArithmeticOperator.EQUAL, Boolean.TRUE);
			queryBuilder.addFilter(filter);
		}
		return getServiceFactory().readerService(Person.class, queryBuilder.build(), IPerson.ALL_PROPERTIES);
	}

	private IReaderService<Void> createRolesOfPersonsReader(final boolean linked) {
		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Role.class);
		queryBuilder.setParentPropertyPath(linked, "personRoleLinks", "person");
		return getServiceFactory().readerService(Role.class, queryBuilder.build(), IRole.ALL_PROPERTIES);
	}

	private IReaderService<Void> createPhonesOfPersonLinkReader(final boolean linked) {
		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Phone.class);
		queryBuilder.setParentPropertyPath(linked, "person");
		return getServiceFactory().readerService(Phone.class, queryBuilder.build(), IPhone.ALL_PROPERTIES);
	}

	private IReaderService<Void> createPersonsOfPhonesLinkReader(final boolean linked) {
		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Person.class);
		queryBuilder.setParentPropertyPath(linked, "phones");
		return getServiceFactory().readerService(Person.class, queryBuilder.build(), IPerson.ALL_PROPERTIES);
	}

	private IReaderService<Void> createSubCategoriesOfCategoriesLinkReader(final boolean linked) {
		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Category.class);
		queryBuilder.setParentPropertyPath(linked, "superCategory");
		return getServiceFactory().readerService(Category.class, queryBuilder.build(), ICategory.ALL_PROPERTIES);
	}

	private IReaderService<Void> createSuperCategoryOfCategoriesLinkReader(final boolean linked) {
		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Category.class);
		queryBuilder.setParentPropertyPath(linked, "subCategories");
		return getServiceFactory().readerService(Category.class, queryBuilder.build(), ICategory.ALL_PROPERTIES);
	}

	private IReaderService<Void> createRolesOfAuthorizationsReader(final boolean linked) {
		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Role.class);
		queryBuilder.setParentPropertyPath(linked, "roleAuthorizationLinks", "authorization");
		return getServiceFactory().readerService(Role.class, queryBuilder.build(), IRole.ALL_PROPERTIES);
	}

	private IReaderService<Void> createAuthorizationsOfRolesReader(final boolean linked) {
		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Authorization.class);
		queryBuilder.setParentPropertyPath(linked, "roleAuthorizationLinks", "role");
		return getServiceFactory().readerService(Authorization.class, queryBuilder.build(), IAuthorization.ALL_PROPERTIES);
	}

	private IReaderService<Void> createCategoriesOfRolesReader(final boolean linked) {
		final ICriteriaQueryCreatorBuilder<Void> queryBuilder = JpaQueryToolkit.criteriaQueryCreatorBuilder(Category.class);
		queryBuilder.setParentPropertyPath(linked, "categoryRoleLinks", "role");
		return getServiceFactory().readerService(Category.class, queryBuilder.build(), ICategory.ALL_PROPERTIES);
	}

	private IUpdaterService createPersonUpdaterService() {
		final IUpdaterServiceBuilder<Person> builder = getServiceFactory().updaterServiceBuilder(Person.class);
		builder.setBeanDtoFactoryAndBeanModifier(IPerson.ALL_PROPERTIES);
		//TODO this validator does not work with h2
		//builder.addBeanValidator(new PersonLoginNameConstraintValidator());
		return builder.build();
	}

	private ICreatorService createPersonCreatorService() {
		final ICreatorServiceBuilder<Person> builder = getServiceFactory().creatorServiceBuilder(Person.class);
		builder.setBeanDtoFactoryAndBeanInitializer(IPerson.ALL_PROPERTIES);
		//TODO this validator does not work with h2
		//builder.addBeanValidator(new PersonLoginNameConstraintValidator());
		return new DecoratedPersonCreatorService(builder.build());
	}
}
