package cloudinvoice.wildfire.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TenantAwareRoutingSource extends AbstractRoutingDataSource {

    private static final String DEFAULT_TENANTID = "icicibank";

	@Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant()!=null?TenantContext.getCurrentTenant():DEFAULT_TENANTID;
    }

}