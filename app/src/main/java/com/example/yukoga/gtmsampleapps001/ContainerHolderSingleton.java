/*
 * Copyright 2014. yukoga@gmail.com. All Rights Reserved.
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

package com.example.yukoga.gtmsampleapps001;

import com.google.android.gms.tagmanager.ContainerHolder;

public class ContainerHolderSingleton {
    private static ContainerHolder containerHolder;

    // constructor
    private ContainerHolderSingleton() {
    }

    // getter
    public static ContainerHolder getContainerHolder() {
        return containerHolder;
    }

    // setter
    public static void setContainerHolder(ContainerHolder argContainerHolder) {
        containerHolder = argContainerHolder;
    }
}