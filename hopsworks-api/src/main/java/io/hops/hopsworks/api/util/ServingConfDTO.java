/*
 * This file is part of Hopsworks
 * Copyright (C) 2018, Logical Clocks AB. All rights reserved
 *
 * Hopsworks is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Hopsworks is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package io.hops.hopsworks.api.util;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServingConfDTO {

  private Integer maxNumInstances;

  public ServingConfDTO () {
  }

  public ServingConfDTO(Integer maxNumInstances) {
    this.maxNumInstances = maxNumInstances;
  }

  public Integer getMaxNumInstances() {
    return maxNumInstances;
  }

  public void setMaxNumInstances(Integer maxNumInstances) {
    this.maxNumInstances = maxNumInstances;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ServingConfDTO that = (ServingConfDTO) o;

    return maxNumInstances != null ? maxNumInstances.equals(that.maxNumInstances) : that.maxNumInstances == null;
  }

  @Override
  public int hashCode() {
    return maxNumInstances != null ? maxNumInstances.hashCode() : 0;
  }
}