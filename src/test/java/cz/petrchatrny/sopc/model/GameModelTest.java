package cz.petrchatrny.sopc.model;

import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.entity.item.OperationNotAllowedException;
import org.junit.jupiter.api.Test;


class GameModelTest {

    @Test
    void processOre() throws OperationNotAllowedException {
        GameModel model = new GameModel();

        model.getInventory().get(ItemType.COAL).setCount(1);
        model.getInventory().get(ItemType.CARBON).setCount(0);

        model.processOre(ItemType.COAL);
        assert model.getInventory().get(ItemType.CARBON).getCount() > 1;
    }

    @Test
    void craftProduct() throws OperationNotAllowedException {
        GameModel model = new GameModel();

        model.getInventory().get(ItemType.IRON).setCount(100);
        model.getInventory().get(ItemType.CARBON).setCount(100);
        model.getInventory().get(ItemType.STEEL).setCount(0);

        model.craftProduct(ItemType.STEEL);

        assert model.getInventory().get(ItemType.CARBON).getCount() < 100;
        assert model.getInventory().get(ItemType.IRON).getCount() < 100;
        assert model.getInventory().get(ItemType.STEEL).getCount() > 0;
    }
}