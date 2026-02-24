package co.id.gpay.wallet.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Response extends Serializable {
}
