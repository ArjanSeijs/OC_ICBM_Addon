package com.eternalsoap.icbmopencomputersaddon.util;

import li.cil.oc.api.Network;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;

/*
    Derived from work Copyright (c) 2013-2015 Florian "Sangar" Nücke published under MIT license
*/

public class ManagedTileEntityEnvironment<T> extends AbstractManagedEnvironment {
    protected final T tileEntity;

    public ManagedTileEntityEnvironment(final T tileEntity, final String name) {
        this.tileEntity = tileEntity;

        setNode(Network.newNode(this, Visibility.Network).
                withComponent(name).
                create());
    }
}
