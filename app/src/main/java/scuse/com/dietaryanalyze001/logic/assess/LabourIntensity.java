package scuse.com.dietaryanalyze001.logic.assess;

import scuse.com.dietaryanalyze001.logic.subclass.UserInfo;

/**
 * Created by Polylanger on 2016/10/17.
 */
public class LabourIntensity {

    private String labourIntensity;

    public LabourIntensity() {
        this.labourIntensity = new LabourIntensityFactory().getLabourIntesity();
    }

    public String getLabourIntensity() {
        return this.labourIntensity;
    }

    interface ChangedIntensity {
        int change();
    }

    public class IntensityFactory {

        protected int intensity = 0;

        public IntensityFactory() {
        }

        public void setIntensity(int intensity) {
            this.intensity = intensity;
        }

        public int getIntensity() {
            return intensity;
        }
    }

    public class LabourIntensityFactory extends IntensityFactory {

        public LabourIntensityFactory() {
            super();

            this.intensity += new WorkIntensityFactory().change();
            this.intensity += new WorkTimeFactory().change();
            this.intensity += new FreeActiveFactory().change();
        }

        public String getLabourIntesity() {
            if (this.intensity > 150) {
                return Constant.LABOUR_HEAVY;
            } else if (this.intensity > 100) {
                return Constant.LABOUR_MIDDLE;
            } else {
                return Constant.LABOUR_LIGHT;
            }
        }
    }

    public class WorkIntensityFactory extends IntensityFactory implements ChangedIntensity {

        public WorkIntensityFactory() {
            super();

            String jobKind = UserInfo.getInstance().getJobKind();
            switch (jobKind) {
                case Constant.WORK_HEAVY:
                    setIntensity(150);
                    break;
                case Constant.WORK_AVERAGE:
                    setIntensity(100);
                    break;
                case Constant.WORK_LIGHT:
                    setIntensity(50);
                    break;
                case Constant.WORK_OFFICE:
                    setIntensity(30);
                    break;
                default:
                    setIntensity(0);
                    break;
            }
        }

        @Override
        public int change() {
            return this.intensity;
        }
    }


    public class WorkTimeFactory extends IntensityFactory implements ChangedIntensity {

        public WorkTimeFactory() {
            super();

            int workTime = UserInfo.getInstance().getWorkTime();
            setIntensity(workTime);
        }

        @Override
        public int change() {
            return getIntensity();
        }
    }

    public class FreeActiveFactory extends IntensityFactory implements ChangedIntensity {

        public FreeActiveFactory() {
            super();

            int freeTime = UserInfo.getInstance().getSpareTime();
            String freeActive = UserInfo.getInstance().getFreeIntensity();
            switch (freeActive) {
                case Constant.ACTIVE_VERY:
                    setIntensity((int) (freeTime * 0.5));
                    break;
                case Constant.ACTIVE_QUITE:
                    setIntensity((int) (freeTime * 0.3));
                    break;
                default:
                    setIntensity((int) (freeTime * 0.1));
                    break;
            }
        }

        @Override
        public int change() {
            return getIntensity();
        }
    }

}
