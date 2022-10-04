package ua.goit.hw5.command.pet;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.exeptions.PetNotFoundException;
import ua.goit.hw5.model.Pet;
import ua.goit.hw5.model.PetStatus;
import ua.goit.hw5.services.PetService;
import ua.goit.hw5.view.View;

import java.util.List;

public class PetFindByStatus implements Command {
    public static final String FIND_BY_STATUS = "pet -findByStatus";
    private final PetService petService;
    private final View view;

    public PetFindByStatus(PetService petService, View view) {
        this.petService = petService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(FIND_BY_STATUS);
    }

    @Override
    public void execute(String input) {
        List<Pet> pets;
        view.write("Write status for search");
        try {
            PetStatus status = PetStatus.valueOf(view.read());
            pets = petService.findPetInStoreByStatus(status);
        } catch (IllegalArgumentException ex) {
            view.write("Illegal status. Command terminated");
            return;
        } catch (PetNotFoundException ex) {
            view.write(ex.getMessage());
            return;
        }
        pets.forEach((pet -> view.write(pet.toString())));
    }
}
