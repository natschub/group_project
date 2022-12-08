package edu.upenn.cit594.utils;

public class covidDateData {

    public class covidDataDate {
        public final Integer neg;
        public final Integer pos;
        public final Integer hospitalized;
        public final Integer partially_vaccinated;
        public final Integer fully_vaccinated;
        public final Integer deaths;


        public covidDataDate(Integer neg, Integer pos, Integer hospitalized, Integer partially_vaccinated, Integer fully_vaccinated, Integer deaths) {
            this.neg = neg;
            this.pos = pos;
            this.hospitalized = hospitalized;
            this.partially_vaccinated = partially_vaccinated;
            this.fully_vaccinated = fully_vaccinated;
            this.deaths = deaths;

        }

        public Integer getNeg() {
            return neg;
        }

        public Integer getPos() {
            return pos;
        }

        public Integer getHospitalized() {
            return hospitalized;
        }

        public Integer getPartially_vaccinated() {
            return partially_vaccinated;
        }

        public Integer getFully_vaccinated() {
            return fully_vaccinated;
        }

        public Integer getDeaths() {
            return deaths;
        }
    }
}
