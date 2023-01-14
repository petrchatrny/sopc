package cz.petrchatrny.sopc.model;

import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.entity.item.OperationNotAllowedException;
import org.junit.jupiter.api.Test;


class GameModelTest {

    @Test
    void processOre() throws OperationNotAllowedException {
        SinglePlayerModel model = new SinglePlayerModel();

        model.getLocalPlayerInventory().get(ItemType.COAL).setCount(1);
        model.getLocalPlayerInventory().get(ItemType.CARBON).setCount(0);

        model.processOre(ItemType.COAL);
        assert model.getLocalPlayerInventory().get(ItemType.CARBON).getCount() > 1;
    }

    @Test
    void craftProduct() throws OperationNotAllowedException {
        SinglePlayerModel model = new SinglePlayerModel();

        model.getLocalPlayerInventory().get(ItemType.IRON).setCount(100);
        model.getLocalPlayerInventory().get(ItemType.CARBON).setCount(100);
        model.getLocalPlayerInventory().get(ItemType.STEEL).setCount(0);

        model.craftProduct(ItemType.STEEL);

        assert model.getLocalPlayerInventory().get(ItemType.CARBON).getCount() < 100;
        assert model.getLocalPlayerInventory().get(ItemType.IRON).getCount() < 100;
        assert model.getLocalPlayerInventory().get(ItemType.STEEL).getCount() > 0;
    }
}