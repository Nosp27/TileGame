package equipment;

import heroes.Buff;

import java.util.List;

public abstract class Equipment {
    public final EquipmentClass equipmentClass;
    public List<Buff> buffs;

    protected Equipment(EquipmentClass equipmentClass) {
        this.equipmentClass = equipmentClass;
    }
}
