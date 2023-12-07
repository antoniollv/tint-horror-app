package com.mapfre.tron.api.cmn.bo;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapfre.dgtp.gaia.commons.annotation.GaiaBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Data object property for RequestSFMCEvent.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 25 may. 2021 - 16:31:13
 *
 */
@Data
@GaiaBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "SFMC - Event")
@Validated
public class EventData {

    @JsonProperty("SubscriberKey")
    @ApiModelProperty(example = "CIP-12345678", required = false, value = "Suscriber Key")
    private String subscriberKey;

    @JsonProperty("EmailAddress")
    @ApiModelProperty(example = "numma@mapfre.com", required = false, value = "Email address")
    private String emailAddress;

    @JsonProperty("Name")
    @ApiModelProperty(example = "myname", required = false, value = "Name")
    private String name;

    @JsonProperty("Product")
    @ApiModelProperty(example = "Su seguro de Auto", required = false, value = "Product")
    private String product;

    @JsonProperty("Actions")
    @ApiModelProperty(example = "<ul><li>Indicar el inicio de vigencia</li><li>Confirmar método de pago</li></ul>", required = false, value = "Actions")
    private String actions;

    @JsonProperty("Code")
    @ApiModelProperty(example = "", required = false, value = "")
    private String code;

    @JsonProperty("Link")
    @ApiModelProperty(example = "", required = false, value = "")
    private String link;

    @JsonProperty("ImageURL")
    @ApiModelProperty(example = "", required = false, value = "")
    private String imageURL;

    @JsonProperty("Subject")
    @ApiModelProperty(example = "", required = false, value = "")
    private String subject;

    @JsonProperty("Date")
    @ApiModelProperty(example = "", required = false, value = "")
    private String date;

    @JsonProperty("Phone")
    @ApiModelProperty(example = "", required = false, value = "")
    private String phone;

    @JsonProperty("Renewal")
    @ApiModelProperty(example = "", required = false, value = "")
    private String renewal;

    @JsonProperty("Vehicle")
    @ApiModelProperty(example = "", required = false, value = "")
    private String vehicle;

    @JsonProperty("PolicyNumber")
    @ApiModelProperty(example = "", required = false, value = "")
    private String policyNumber;

    @JsonProperty("PolicyModality")
    @ApiModelProperty(example = "", required = false, value = "")
    private String policyModality;

    @JsonProperty("Data")
    @ApiModelProperty(example = "", required = false, value = "")
    private String data;

    @JsonProperty("NewValue")
    @ApiModelProperty(example = "", required = false, value = "")
    private String newValue;

    @JsonProperty("Risk")
    @ApiModelProperty(example = "", required = false, value = "")
    private String risk;

    @JsonProperty("DocumentLink")
    @ApiModelProperty(example = "", required = false, value = "")
    private String documentLink;

    @JsonProperty("ReceiptNumber")
    @ApiModelProperty(example = "", required = false, value = "")
    private String receiptNumber;

    @JsonProperty("Entity")
    @ApiModelProperty(example = "", required = false, value = "")
    private String entity;

    @JsonProperty("AccountNumber")
    @ApiModelProperty(example = "", required = false, value = "")
    private String accountNumber;

    @JsonProperty("ClaimNumber")
    @ApiModelProperty(example = "", required = false, value = "")
    private String claimNumber;

    @JsonProperty("OpeningDate")
    @ApiModelProperty(example = "", required = false, value = "")
    private String openingDate;

    @JsonProperty("ProffesionalInfo")
    @ApiModelProperty(example = "", required = false, value = "")
    private String proffesionalInfo;

    @JsonProperty("ProffesionalName")
    @ApiModelProperty(example = "", required = false, value = "")
    private String proffesionalName;

    @JsonProperty("Address")
    @ApiModelProperty(example = "", required = false, value = "")
    private String address;

    @JsonProperty("Time")
    @ApiModelProperty(example = "", required = false, value = "")
    private String time;



}
