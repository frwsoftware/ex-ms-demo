
package frwsoftware.model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "labels",
    "descriptions",
    "aliases",
    "statements",
    "sitelinks",
    "id"
})
@Generated("jsonschema2pojo")
public class WikidataResp {

    @JsonProperty("type")
    private String type;
    @JsonProperty("labels")
    private Labels labels;
    @JsonProperty("descriptions")
    private Descriptions descriptions;
    @JsonProperty("aliases")
    private Aliases aliases;


    @JsonProperty("id")
    private String id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public WikidataResp withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("labels")
    public Labels getLabels() {
        return labels;
    }

    @JsonProperty("labels")
    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public WikidataResp withLabels(Labels labels) {
        this.labels = labels;
        return this;
    }

    @JsonProperty("descriptions")
    public Descriptions getDescriptions() {
        return descriptions;
    }

    @JsonProperty("descriptions")
    public void setDescriptions(Descriptions descriptions) {
        this.descriptions = descriptions;
    }

    public WikidataResp withDescriptions(Descriptions descriptions) {
        this.descriptions = descriptions;
        return this;
    }

    @JsonProperty("aliases")
    public Aliases getAliases() {
        return aliases;
    }

    @JsonProperty("aliases")
    public void setAliases(Aliases aliases) {
        this.aliases = aliases;
    }

    public WikidataResp withAliases(Aliases aliases) {
        this.aliases = aliases;
        return this;
    }



    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public WikidataResp withId(String id) {
        this.id = id;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public WikidataResp withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }


}
