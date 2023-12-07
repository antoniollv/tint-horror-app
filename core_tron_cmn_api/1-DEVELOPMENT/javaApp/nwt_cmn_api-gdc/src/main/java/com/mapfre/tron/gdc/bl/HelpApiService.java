package com.mapfre.tron.gdc.bl;

import java.util.List;
import com.mapfre.tron.gdc.sr.dto.DataIn;
import com.mapfre.tron.gdc.sr.dto.HelpValue;

public interface HelpApiService {
  List<HelpValue> help(DataIn body, String idHelp, Integer cmpVal);
}
