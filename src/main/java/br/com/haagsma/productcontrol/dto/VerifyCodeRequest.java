package br.com.haagsma.productcontrol.dto;

import br.com.haagsma.productcontrol.model.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class VerifyCodeRequest implements Serializable {

    private String email;
    private String code;

}
