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

import com.hazelcast.config.NetworkConfig;
import com.hazelcast.logging.ILogger;
import com.hazelcast.nio.Address;
import com.hazelcast.spi.discovery.AbstractDiscoveryStrategy;
import com.hazelcast.spi.discovery.DiscoveryNode;
import com.hazelcast.spi.discovery.SimpleDiscoveryNode;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static am.ik.hazelcast.dns.DnsRestServiceProperties.HOSTNAME;
import static am.ik.hazelcast.dns.DnsRestServiceProperties.PORT;

public class DnsServiceDiscoveryStrategy extends AbstractDiscoveryStrategy {

    private final String hostname;
    private final int port;
    private final ILogger logger;

    public DnsServiceDiscoveryStrategy(ILogger logger, //
                                       Map<String, Comparable> properties) {
        super(logger, properties);
        this.hostname = getOrNull(HOSTNAME);
        this.port = getOrDefault(PORT, NetworkConfig.DEFAULT_PORT);
        this.logger = logger;
        logger.info("DnsServiceDiscoveryStrategy: created {hostname=" //
                + this.hostname + "}");
    }

    @Override
    public Iterable<DiscoveryNode> discoverNodes() {
        List<DiscoveryNode> discoveryNodes = new ArrayList<>();
        try {
            InetAddress[] addresses = InetAddress.getAllByName(hostname);
            for (InetAddress address : addresses) {
                discoveryNodes.add(new SimpleDiscoveryNode(new Address(address.getHostAddress(), this.port)));
            }
        } catch (UnknownHostException e) {
            this.logger.warning(this.hostname + " can not be resolved", e);
        }
        return discoveryNodes;
    }

    @Override
    public void start() {
        logger.info("DnsServiceDiscoveryStrategy: started ");
    }

    @Override
    public void destroy() {
        logger.info("DnsServiceDiscoveryStrategy: destroyed");
    }
}