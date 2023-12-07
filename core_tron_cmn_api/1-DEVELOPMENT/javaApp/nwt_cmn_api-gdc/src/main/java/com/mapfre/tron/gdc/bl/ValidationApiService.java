package com.mapfre.tron.gdc.bl;

import java.util.List;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

public interface ValidationApiService {
  List<ValidationError> conceptValidation(DataInExtended body);
}
