/*
 *  Copyright 2014~2015 Dan Haywood
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
package org.isisaddons.module.xdocreport.integtests;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.isisaddons.module.xdocreport.fixture.dom.Order;
import org.isisaddons.module.xdocreport.fixture.dom.Orders;
import org.isisaddons.module.xdocreport.fixture.scripts.XdocreportModuleAppSetupFixture;

public class XdocreportDemoObjectsTest extends XdocreportModuleIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private Orders orders;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new XdocreportModuleAppSetupFixture(), null);
    }

    @Test
    public void listAll() throws Exception {
        final List<Order> all = wrap(orders).listAll();
        Assertions.assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void create() throws Exception {

        wrap(orders).create("1234", "John Doe", new LocalDate(2015, 1, 1), "Need to sign personally");

        final List<Order> all = wrap(orders).listAll();
        Assertions.assertThat(all.size()).isEqualTo(2);
    }

}