<template>
  <v-container>
    <v-row>
      <v-col cols="6" offset="3">
        <v-card>
          <v-card-title>Anmeldung</v-card-title>
          <v-card-text>
            <v-form>
              <v-text-field
                  name="login"
                  label="E-Mail"
                  placeholder="john.doe@mail.com"
                  type="email"
                  prepend-icon="mdi-account"
              ></v-text-field>
              <v-text-field
                  name="password"
                  label="Passwort"
                  type="password"
                  prepend-icon="mdi-lock"
              ></v-text-field>
            </v-form>

            <!--            <v-btn block color="primary" to="/">Anmelden</v-btn>-->

            <!--            <br/>-->
            <!--            <p class="text-center">-->
            <!--              Noch kein Konto? <a href="/register">Konto erstellen</a>.-->
            <!--            </p>-->
          </v-card-text>
          <v-divider></v-divider>
          <v-card-actions>
            <v-btn color="primary" to="/">Anmelden</v-btn>
            <v-btn to="/register">Registrieren</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import {defineComponent} from 'vue'

/**
 * View to allow users to login. Not in use.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "LoginView",
  components: {},
  data() {
    return {}
  },
  methods: {},
  mounted() {
  }
})
</script>

<style>

</style>