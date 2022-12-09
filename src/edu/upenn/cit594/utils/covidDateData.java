package edu.upenn.cit594.utils;
import java.util.Date;

public class covidDateData {

        private final Date date;
        private final String zipCode;
        private final Integer neg;
        private final Integer pos;
        private final Integer hospitalized;
        private final Integer partially_vaccinated;
        private final Integer fully_vaccinated;
        private final Integer deaths;



        public covidDateData(Date date, String zipCode, Integer neg, Integer pos, Integer hospitalized, Integer partially_vaccinated, Integer fully_vaccinated, Integer deaths) {
            this.date = date;
            this.zipCode = zipCode;
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
        public String getZipCode() {return zipCode;}
        public Date getDate() {
            return date;
        }

}
