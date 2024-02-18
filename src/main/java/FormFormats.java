/**
 * Java Class to save the format of the different pdfs you could fill in
 *
 * @author Dawson Pent
 * @version 1.0
 */
public class FormFormats {
    /**
     * Method to return the format to the given specified form.
     *
     * @param form  Integer representing the number of the Form format
     * @return      format for the given Form
     */
    public static String[][] getForm(int form) {
        switch(form) {
            case 0:
                //returning the POA Form format
                return POAForm();
            default:
                //Inputed form number is invalid
                return null;
        }
    }

    public static String[][] POAForm() {
        return new String[][] {
                //specifying form name
                {"POA Form"},

                //specifying how many inputs
                {"10"},
                //-specifying inputs
                //section 1 (general info)
                {"Company", "Company", "DropDown", "Coweta Hearing Clinic", "Fayette Hearing Clinic"},
                {"Provider", "Provider", "Text"},
                {"Patient ID#", "PatientID", "Text"},
                {"Insurance Company", "InsuranceCompany", "Text"},
                {"Insurance Plan", "InsurancePlan", "Text"},

                //section 2 (hearing aid info)
                {"Total Hearing Aid Price", "Total", "Number"},
                {"Standard Benefit Amount Allowed", "AllowedAmount", "Number"},
                {"Remaining Deductible", "DeductibleAmount", "Number"},
                {"Patient Copay", "Copayment", "Number"},
                {"Patient Coinsurance %", "CoinsurancePercent", "Percent"},

                //specifying the amount of outputs to calculate
                {"7"},
                //specifying the output IDs and what math is necessary for them
                {"Coinsurance Amount", "CoinsuranceAmount", "I6", "-", "I7", "*", "I9"},
                {"Price Difference", "PriceDifference", "I5", "-", "I6"},
                {"Expected Reimbursement", "ExpectedReimbursement", "I6", "-", "I7", "I8", "O0"},
                {"Total Responsibility", "TotalResponsibility", "I5", "-", "O2"},
                {"Estimated Price", "EstimatedPrice", "I6"},
                {"Total Price", "TotalPrice", "I5"},
                {"Total Difference", "TotalDifference", "O1"},

                //PDF name
                {"POAFormFillable.pdf"}
        };
    }
}
