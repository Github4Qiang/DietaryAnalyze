package scuse.com.dietaryanalyze001.logic.assess;

import java.util.ArrayList;

/**
 * Created by Polylanger on 2016/10/17.
 */
public class DRIsStandard {

    private ArrayList<DRIsCell> cells = new ArrayList<>();
    private int[] AGE_BRACKET = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 14, 18, 50, 60, 70, 80, Integer.MAX_VALUE};

    public DRIsStandard() {
        // 男 0-17 岁
        cells.add(new DRIsCell(AGE_BRACKET[0], AGE_BRACKET[1], Constant.GENDER_MALE, null, 95, 3, 4, 12));
        cells.add(new DRIsCell(AGE_BRACKET[1], AGE_BRACKET[2], Constant.GENDER_MALE, null, 1100, 35, 43, 144));
        cells.add(new DRIsCell(AGE_BRACKET[2], AGE_BRACKET[3], Constant.GENDER_MALE, null, 1200, 40, 43, 164));
        cells.add(new DRIsCell(AGE_BRACKET[3], AGE_BRACKET[4], Constant.GENDER_MALE, null, 1350, 45, 45, 191));
        cells.add(new DRIsCell(AGE_BRACKET[4], AGE_BRACKET[5], Constant.GENDER_MALE, null, 1450, 50, 48, 204));
        cells.add(new DRIsCell(AGE_BRACKET[5], AGE_BRACKET[6], Constant.GENDER_MALE, null, 1600, 55, 53, 225));
        cells.add(new DRIsCell(AGE_BRACKET[6], AGE_BRACKET[7], Constant.GENDER_MALE, null, 1700, 55, 57, 243));
        cells.add(new DRIsCell(AGE_BRACKET[7], AGE_BRACKET[8], Constant.GENDER_MALE, null, 1800, 60, 56, 264));
        cells.add(new DRIsCell(AGE_BRACKET[8], AGE_BRACKET[9], Constant.GENDER_MALE, null, 1900, 65, 59, 277));
        cells.add(new DRIsCell(AGE_BRACKET[9], AGE_BRACKET[10], Constant.GENDER_MALE, null, 2000, 65, 62, 295));
        cells.add(new DRIsCell(AGE_BRACKET[10], AGE_BRACKET[11], Constant.GENDER_MALE, null, 2100, 70, 65, 308));
        cells.add(new DRIsCell(AGE_BRACKET[11], AGE_BRACKET[12], Constant.GENDER_MALE, null, 2400, 75, 75, 357));
        cells.add(new DRIsCell(AGE_BRACKET[12], AGE_BRACKET[13], Constant.GENDER_MALE, null, 2900, 85, 90, 437));
        // 男 18-49 岁
        cells.add(new DRIsCell(AGE_BRACKET[13], AGE_BRACKET[14], Constant.GENDER_MALE, Constant.LABOUR_LIGHT, 2400, 75, 67, 375));
        cells.add(new DRIsCell(AGE_BRACKET[13], AGE_BRACKET[14], Constant.GENDER_MALE, Constant.LABOUR_MIDDLE, 2700, 80, 75, 426));
        cells.add(new DRIsCell(AGE_BRACKET[13], AGE_BRACKET[14], Constant.GENDER_MALE, Constant.LABOUR_HEAVY, 3200, 90, 89, 510));
        // 男 50-59 岁
        cells.add(new DRIsCell(AGE_BRACKET[14], AGE_BRACKET[15], Constant.GENDER_MALE, Constant.LABOUR_LIGHT, 2300, 75, 64, 356));
        cells.add(new DRIsCell(AGE_BRACKET[14], AGE_BRACKET[15], Constant.GENDER_MALE, Constant.LABOUR_MIDDLE, 2600, 80, 72, 408));
        cells.add(new DRIsCell(AGE_BRACKET[14], AGE_BRACKET[15], Constant.GENDER_MALE, Constant.LABOUR_HEAVY, 3100, 90, 86, 491));
        // 男 60-69 岁
        cells.add(new DRIsCell(AGE_BRACKET[15], AGE_BRACKET[16], Constant.GENDER_MALE, Constant.LABOUR_LIGHT, 1900, 75, 53, 281));
        cells.add(new DRIsCell(AGE_BRACKET[15], AGE_BRACKET[16], Constant.GENDER_MALE, Constant.LABOUR_MIDDLE, 2200, 75, 61, 338));
        // 男 70-79 岁
        cells.add(new DRIsCell(AGE_BRACKET[16], AGE_BRACKET[17], Constant.GENDER_MALE, Constant.LABOUR_LIGHT, 1900, 75, 53, 281));
        cells.add(new DRIsCell(AGE_BRACKET[16], AGE_BRACKET[17], Constant.GENDER_MALE, Constant.LABOUR_MIDDLE, 2200, 75, 58, 319));
        // 男 80+
        cells.add(new DRIsCell(AGE_BRACKET[17], AGE_BRACKET[18], Constant.GENDER_MALE, null, 1900, 75, 53, 281));

        // 女 0-17 岁
        cells.add(new DRIsCell(AGE_BRACKET[0], AGE_BRACKET[1], Constant.GENDER_FEMALE, null, 95, 3, 4, 12));
        cells.add(new DRIsCell(AGE_BRACKET[1], AGE_BRACKET[2], Constant.GENDER_FEMALE, null, 1050, 35, 41, 136));
        cells.add(new DRIsCell(AGE_BRACKET[2], AGE_BRACKET[3], Constant.GENDER_FEMALE, null, 1150, 40, 41, 156));
        cells.add(new DRIsCell(AGE_BRACKET[3], AGE_BRACKET[4], Constant.GENDER_FEMALE, null, 1300, 45, 43, 183));
        cells.add(new DRIsCell(AGE_BRACKET[4], AGE_BRACKET[5], Constant.GENDER_FEMALE, null, 1400, 50, 47, 195));
        cells.add(new DRIsCell(AGE_BRACKET[5], AGE_BRACKET[6], Constant.GENDER_FEMALE, null, 1500, 55, 50, 208));
        cells.add(new DRIsCell(AGE_BRACKET[6], AGE_BRACKET[7], Constant.GENDER_FEMALE, null, 1600, 55, 53, 225));
        cells.add(new DRIsCell(AGE_BRACKET[7], AGE_BRACKET[8], Constant.GENDER_FEMALE, null, 1700, 60, 53, 246));
        cells.add(new DRIsCell(AGE_BRACKET[8], AGE_BRACKET[9], Constant.GENDER_FEMALE, null, 1800, 65, 56, 259));
        cells.add(new DRIsCell(AGE_BRACKET[9], AGE_BRACKET[10], Constant.GENDER_FEMALE, null, 1900, 65, 59, 277));
        cells.add(new DRIsCell(AGE_BRACKET[10], AGE_BRACKET[11], Constant.GENDER_FEMALE, null, 2000, 65, 62, 295));
        cells.add(new DRIsCell(AGE_BRACKET[11], AGE_BRACKET[12], Constant.GENDER_FEMALE, null, 2200, 75, 68, 321));
        cells.add(new DRIsCell(AGE_BRACKET[12], AGE_BRACKET[13], Constant.GENDER_FEMALE, null, 2400, 80, 75, 352));
        // 女 18-49 岁
        cells.add(new DRIsCell(AGE_BRACKET[13], AGE_BRACKET[14], Constant.GENDER_FEMALE, Constant.LABOUR_LIGHT, 2100, 65, 58, 329));
        cells.add(new DRIsCell(AGE_BRACKET[13], AGE_BRACKET[14], Constant.GENDER_FEMALE, Constant.LABOUR_MIDDLE, 2300, 70, 64, 361));
        cells.add(new DRIsCell(AGE_BRACKET[13], AGE_BRACKET[14], Constant.GENDER_FEMALE, Constant.LABOUR_HEAVY, 2700, 80, 75, 426));
        // 女 50-59 岁
        cells.add(new DRIsCell(AGE_BRACKET[14], AGE_BRACKET[15], Constant.GENDER_FEMALE, Constant.LABOUR_LIGHT, 1900, 65, 53, 291));
        cells.add(new DRIsCell(AGE_BRACKET[14], AGE_BRACKET[15], Constant.GENDER_FEMALE, Constant.LABOUR_MIDDLE, 2000, 70, 56, 305));
        cells.add(new DRIsCell(AGE_BRACKET[14], AGE_BRACKET[15], Constant.GENDER_FEMALE, Constant.LABOUR_HEAVY, 2200, 80, 61, 333));
        // 女 60-69 岁
        cells.add(new DRIsCell(AGE_BRACKET[15], AGE_BRACKET[16], Constant.GENDER_FEMALE, Constant.LABOUR_LIGHT, 1800, 65, 50, 273));
        cells.add(new DRIsCell(AGE_BRACKET[15], AGE_BRACKET[16], Constant.GENDER_FEMALE, Constant.LABOUR_MIDDLE, 2000, 65, 56, 310));
        // 女 70-79 岁
        cells.add(new DRIsCell(AGE_BRACKET[16], AGE_BRACKET[17], Constant.GENDER_FEMALE, Constant.LABOUR_LIGHT, 1700, 65, 47, 254));
        cells.add(new DRIsCell(AGE_BRACKET[16], AGE_BRACKET[17], Constant.GENDER_FEMALE, Constant.LABOUR_MIDDLE, 1900, 65, 53, 291));
        // 女 80+
        cells.add(new DRIsCell(AGE_BRACKET[17], AGE_BRACKET[18], Constant.GENDER_FEMALE, null, 1700, 65, 47, 254));
    }

    public class DRIsCell {
        private int ageStt;
        private int ageEnd;
        private String gender;
        private String labourIntensity;
        private int eng;
        private int prt;
        private int fat;
        private int cab;

        public DRIsCell(int ageStt, int ageEnd, String gender, String labourIntensity,
                        int eng, int prt, int fat, int cab) {
            this.ageStt = ageStt;
            this.ageEnd = ageEnd;
            this.gender = gender;
            this.labourIntensity = labourIntensity;
            this.eng = eng;
            this.prt = prt;
            this.fat = fat;
            this.cab = cab;
        }

        public int getAgeStt() {
            return ageStt;
        }

        public int getAgeEnd() {
            return ageEnd;
        }

        public String getGender() {
            return gender;
        }

        public String getLabourIntensity() {
            return labourIntensity;
        }

        public int getEng() {
            return eng;
        }

        public int getPrt() {
            return prt;
        }

        public int getFat() {
            return fat;
        }

        public int getCab() {
            return cab;
        }
    }

    public ArrayList<DRIsCell> getCells() {
        return this.cells;
    }
}
