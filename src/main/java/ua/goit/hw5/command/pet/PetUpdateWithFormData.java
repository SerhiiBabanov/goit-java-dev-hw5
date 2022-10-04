package ua.goit.hw5.command.pet;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.model.ApiResponse;
import ua.goit.hw5.model.PetStatus;
import ua.goit.hw5.services.PetService;
import ua.goit.hw5.view.View;

public class PetUpdateWithFormData implements Command {
    public static final String UPDATE_BY_FORM_DATA = "pet -updateByFormData";
    private final PetService petService;
    private final View view;

    public PetUpdateWithFormData(PetService petService, View view) {
        this.petService = petService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(UPDATE_BY_FORM_DATA);
    }

    @Override
    public void execute(String input) {
        view.write("Write pet id");
        Long petId = Long.valueOf(view.read());
        view.write("Write pet name");
        String petName = view.read();
        view.write("Write pet status -> available, pending, sold");
        PetStatus petStatus;
        try {
            petStatus = PetStatus.valueOf(view.read());
        } catch (IllegalArgumentException ex) {
            view.write("Illegal pet status. Command terminated");
            return;
        }
        ApiResponse response = petService.updatePetInStoreByFormData(petId, petName, petStatus);
        view.write(response.toString());
    }
}
