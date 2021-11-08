import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectedListControllerTest {

    @Test
    void addToListTest() {
        SelectedListController x = new SelectedListController();
        x.addToList("Homework", "finish math homework", "2021-12-15", "N");

        assertTrue(x.getObservableList().size() > 0);
    }

    @Test
    void clearFullListTest() {
        SelectedListController x = new SelectedListController();
        x.addToList("Homework", "finish math homework", "2021-12-15", "N");

        x.clearFullList();

        assertTrue(x.getObservableList().size() == 0);

    }

    @Test
    void notPopulatedCheck() {
        SelectedListController x = new SelectedListController();
        assertTrue(x.notPopulatedCheck(""));

    }

    @Test
    void PopulatedCheck() {
        SelectedListController x = new SelectedListController();
        assertFalse(x.notPopulatedCheck("this string is populated"));

    }

    @Test
    void dateFormatCheck() {
        SelectedListController x = new SelectedListController();
        assertFalse(x.dateFormatCheck("2001-12-15"));
    }

    @Test
    void dateFormatCheckFalse() {
        SelectedListController x = new SelectedListController();
        assertTrue(x.dateFormatCheck("20-12-15"));
    }

    @Test
    void dateExistCheck() {
        SelectedListController x = new SelectedListController();
        assertTrue(x.dateExistCheck(2001,12,15));

    }

    @Test
    void dateExistCheckFalse() {
        SelectedListController x = new SelectedListController();
        assertFalse(x.dateExistCheck(2001,13,15));

    }
}