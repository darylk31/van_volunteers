package cs.ubc.ca.van_volunteers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends PreferenceFragment {

    private String account_type;
    private FirebaseAuth mAuth;
    private Preference SignOut;
    SharedPreferences pref;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        pref = getActivity().getSharedPreferences(Utils.APP_PACKAGE, MODE_PRIVATE);
        account_type = pref.getString("account_type", "");
        if (account_type.equals(Utils.ACCOUNT_TYPE_VOLUNTEER)) {
            addPreferencesFromResource(R.xml.volunteer_pref);
        } else {
            addPreferencesFromResource(R.xml.organization_pref);
        }
        setUpMenu();
    }

    private void setUpMenu() {
        SignOut = findPreference("pref_key_signout");
        SignOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                pref.edit().clear().commit();
                mAuth.signOut();
                startActivity(new Intent(getActivity(), HomeActivity.class));
                return false;
            }
        });
    }


}
