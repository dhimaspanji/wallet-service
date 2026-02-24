package co.id.gpay.wallet.model.base.data;

import co.id.gpay.wallet.model.base.Request;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("data")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public interface BaseDataRequest extends Request {
}
