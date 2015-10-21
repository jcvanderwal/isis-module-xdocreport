/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.xdocreport.fixture.dom;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject(
    objectType = "ORDER"
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@MemberGroupLayout(columnSpans = {6,0,0,6})
public class Order implements Comparable<Order> {

    //region > number (property)
    // //////////////////////////////////////
    private String number;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence = "1")
    @MemberOrder(sequence = "1")
    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }
    //endregion

    //region > customerName (property)

    private String customerName;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(prepend= ", ", sequence="2")
    @MemberOrder(sequence = "1")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    //endregion

    //region > date (property)

    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    private LocalDate date;

    @javax.jdo.annotations.Column(allowsNull="true")
    @MemberOrder(sequence = "1")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }
    //endregion

    //region > preferences (property)
    private String preferences;

    @javax.jdo.annotations.Column(allowsNull="true")
    @PropertyLayout(
            multiLine = 6
    )
    @MemberOrder(sequence = "4")
    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(final String preferences) {
        this.preferences = preferences;
    }
    //endregion

    //region > orderLines (collection)

    @javax.jdo.annotations.Persistent(mappedBy = "order")
    private SortedSet<OrderLine> orderLines = new TreeSet<OrderLine>();

    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public SortedSet<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(final SortedSet<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void addToOrderLines(final OrderLine orderLine) {
        getOrderLines().add(orderLine);
    }
    public void removeFromOrderLines(final OrderLine orderLine) {
        getOrderLines().remove(orderLine);
    }

    @MemberOrder(name = "orderLines", sequence = "1")
    public Order add(
            @ParameterLayout(named="Description")
            final String description,
            @ParameterLayout(named="Cost")
            final BigDecimal cost,
            @ParameterLayout(named="Quantity")
            final int quantity) {

        final OrderLine orderLine = container.newTransientInstance(OrderLine.class);
        orderLine.setCost(cost);
        orderLine.setDescription(description);
        orderLine.setQuantity(quantity);
        getOrderLines().add(orderLine); // will set the parent on the OrderLine

        container.persistIfNotAlready(orderLine);

        return this;
    }

    @MemberOrder(name = "orderLines", sequence = "2")
    public Order remove(final OrderLine orderLine) {
        removeFromOrderLines(orderLine);
        return this;
    }

    public Collection<OrderLine> choices0Remove() {
        return getOrderLines();
    }


    //endregion

    //region > compareTo

    @Override
    public int compareTo(Order other) {
        return ObjectContracts.compare(this, other, "number");
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion

}
