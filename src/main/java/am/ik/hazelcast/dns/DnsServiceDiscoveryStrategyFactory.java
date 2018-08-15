/*
 * Copyright (C) 2018 Toshiaki Maki <makingx@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package am.ik.hazelcast.dns;

import com.hazelcast.config.properties.PropertyDefinition;
import com.hazelcast.logging.ILogger;
import com.hazelcast.spi.discovery.DiscoveryNode;
import com.hazelcast.spi.discovery.DiscoveryStrategy;
import com.hazelcast.spi.discovery.DiscoveryStrategyFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static am.ik.hazelcast.dns.DnsServiceDiscoveryProperties.HOSTNAME;
import static am.ik.hazelcast.dns.DnsServiceDiscoveryProperties.PORT;

public class DnsServiceDiscoveryStrategyFactory implements DiscoveryStrategyFactory {

    private static final Collection<PropertyDefinition> PROPERTY_DEFINITIONS =
            Collections.unmodifiableCollection(
                    Arrays.asList(HOSTNAME, PORT));

    @Override
    public Class<? extends DiscoveryStrategy> getDiscoveryStrategyType() {
        return DnsServiceDiscoveryStrategy.class;
    }

    @Override
    public DiscoveryStrategy newDiscoveryStrategy(DiscoveryNode discoveryNode,
                                                  ILogger logger, Map<String, Comparable> properties) {
        logger.info("DnsServiceDiscoveryStrategyFactory.newDiscoveryStrategy(properties=" + properties + ")");
        return new DnsServiceDiscoveryStrategy(logger, properties);
    }

    @Override
    public Collection<PropertyDefinition> getConfigurationProperties() {
        return PROPERTY_DEFINITIONS;
    }
}