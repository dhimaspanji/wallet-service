package co.id.gpay.wallet.header;

import co.id.gpay.wallet.utils.ChannelType;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record RequestHeader(
        String requestId,
        ChannelType channel
) {
}
