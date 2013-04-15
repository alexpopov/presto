package com.facebook.presto.connector;

import com.facebook.presto.metadata.ConnectorMetadata;
import com.facebook.presto.metadata.ImportMetadata;
import com.facebook.presto.metadata.MetadataManager;
import com.facebook.presto.spi.ImportClient;
import com.facebook.presto.split.ImportClientManager;
import com.facebook.presto.split.ImportSplitManager;
import com.facebook.presto.split.SplitManager;
import com.google.inject.Inject;

public class ConnectorManager
{
    private final MetadataManager metadataManager;
    private final SplitManager splitManager;
    private final ImportClientManager importClientManager;

    @Inject
    public ConnectorManager(MetadataManager metadataManager, SplitManager splitManager, ImportClientManager importClientManager)
    {
        this.metadataManager = metadataManager;
        this.splitManager = splitManager;
        this.importClientManager = importClientManager;
    }

    public void initialize()
    {
        // list is lame, but just register all plugins into a catalog for now
        for (String connectorName : importClientManager.getImportClientFactories().keySet()) {
            createConnection(connectorName, connectorName);
        }
    }

    public void createConnection(String catalogName, String connectorName)
    {
        ImportClient client = importClientManager.getClient(catalogName);
        ConnectorMetadata connectorMetadata = new ImportMetadata(catalogName, client);
        metadataManager.addConnectorMetadata(catalogName, connectorMetadata);
        splitManager.addConnectorSplitManager(new ImportSplitManager(catalogName, client));
    }
}
