package com.mapfre.tron.api.vld.api;

import com.mapfre.nwt.exception.NwtException;

public interface ValidationDsbRow {
  public void vld(String dsbRow) throws NwtException;
}
