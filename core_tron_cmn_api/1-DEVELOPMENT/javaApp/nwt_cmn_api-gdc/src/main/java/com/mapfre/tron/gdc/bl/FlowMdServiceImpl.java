package com.mapfre.tron.gdc.bl;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.tron.gdc.sr.dto.DataIn;
import com.mapfre.tron.gdc.sr.dto.FlowMdIn;
import com.mapfre.tron.gdc.sr.dto.FlowMdValue;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service
public class FlowMdServiceImpl implements FlowMdService {

    protected static final String ERROR = "Error";

    @Override
    public List<ValidationError> flowMdAction(String idFlow, Integer step, FlowMdIn body) {
        if ("GeografiaMd".equals(idFlow)) {
            if (body.getData() != null) {
                Optional<FlowMdValue> f = body.getData().stream().filter(x -> "Pais".equals(x.getIdConcept()))
                        .findFirst();
                if (f.isPresent() && f.get().getData() != null && !f.get().getData().isEmpty()
                        && ERROR.equals(f.get().getData().get(0).get("NOMBRE"))) {
                    try {
                        return om.readValue(getClass().getClassLoader().getResourceAsStream("geografia.error.json"),
                                new TypeReference<List<ValidationError>>() {
                                });
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
        } else if ("SolicitudRescate".equals(idFlow)) {
            if (body.getData() != null) {
                Optional<FlowMdValue> f = body.getData().stream().filter(x -> "DatoBancario".equals(x.getIdConcept()))
                        .findFirst();
                if (f.isPresent() && f.get().getData() != null && !f.get().getData().isEmpty()
                        && ERROR.equals(f.get().getData().get(0).get("BANCO"))) {
                    try {
                        return om.readValue(getClass().getClassLoader().getResourceAsStream("rescate.error.json"),
                                new TypeReference<List<ValidationError>>() {
                                });
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
        } else if ("SolicitudRescateV2".equals(idFlow) && step == 3) {
            if (body.getData() != null) {
                Optional<FlowMdValue> f = body.getData().stream().filter(x -> "DatoBancario".equals(x.getIdConcept()))
                        .findFirst();
                if (f.isPresent() && f.get().getData() != null && !f.get().getData().isEmpty()
                        && ERROR.equals(f.get().getData().get(0).get("BANCO"))) {
                    try {
                        return om.readValue(getClass().getClassLoader().getResourceAsStream("rescate.error.json"),
                                new TypeReference<List<ValidationError>>() {
                                });
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
        }
        return null;
    }

    private ObjectMapper om = new ObjectMapper();

    @Override
    public List<FlowMdValue> flowMdPrevious(String idFlow, Integer step, DataIn body) {
        if ("GeografiaMd".equals(idFlow)) {
            try {
                if ("1".equals(body.getData().get("COD_PAIS"))) {
                    return om.readValue(getClass().getClassLoader().getResourceAsStream("geografia_2.json"),
                            new TypeReference<List<FlowMdValue>>() {
                            });
                }
                return om.readValue(getClass().getClassLoader().getResourceAsStream("geografia.json"),
                        new TypeReference<List<FlowMdValue>>() {
                        });
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        } else if ("SolicitudRescate".equals(idFlow) || "SolicitudRescateV2".equals(idFlow)) {
            try {
                InputStream src = getClass().getClassLoader()
                        .getResourceAsStream("rescate-" + body.getData().get("POLIZA") + ".json");
                if (src == null) {
                    src = getClass().getClassLoader().getResourceAsStream("rescate.json");
                }
                return om.readValue(src, new TypeReference<List<FlowMdValue>>() {
                });
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        return null;
    }

}
