package ua.goit.hw5.command.pet;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.model.Pet;
import ua.goit.hw5.services.PetService;
import ua.goit.hw5.view.View;

public class PetUpdateExisting implements Command {
    public static final String UPDATE_EXISTING = "pet -updateExisting";
    private final PetService petService;
    private final View view;

    public PetUpdateExisting(PetService petService, View view) {
        this.petService = petService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(UPDATE_EXISTING);
    }

    @Override
    public void execute(String input) {
        Pet pet = petService.getPet(view);
        Pet addedPet = petService.updatePetInStore(pet);
        view.write(addedPet.toString());
    }
}
