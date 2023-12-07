package com.mapfre.tron.gdc.bl;

import java.util.List;
import com.mapfre.tron.gdc.sr.dto.DataIn;
import com.mapfre.tron.gdc.sr.dto.FlowMdIn;
import com.mapfre.tron.gdc.sr.dto.FlowMdValue;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

public interface FlowMdService {

  List<ValidationError> flowMdAction(String idFlow, Integer step, FlowMdIn body);
  
  List<FlowMdValue> flowMdPrevious(String idFlow, Integer step, DataIn body);
}
