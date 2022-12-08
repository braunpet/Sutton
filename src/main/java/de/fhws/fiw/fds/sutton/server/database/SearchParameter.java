/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.database;

public class SearchParameter {

    public static final SearchParameter DEFAULT = new SearchParameter();

    public static SearchParameter getOrDefault(SearchParameter... searchParameters) {
        if (searchParameters != null && searchParameters.length == 1) {
            return searchParameters[0];
        } else {
            return DEFAULT;
        }
    }

    private int offset = 0;

    private int size = Integer.MAX_VALUE;

    private String orderByAttribute = "";

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrderByAttribute() {
        return this.orderByAttribute;
    }

    public void setOrderByAttribute(String orderByAttribute) {
        this.orderByAttribute = orderByAttribute;
    }

}