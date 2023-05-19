<template>
  <v-container>
    <v-row>
      <v-col cols="6" offset="3">
        <v-card>
          <v-card-title>Konto erstellen</v-card-title>
          <v-card-text>
            <v-form>
              <v-text-field
                  name="login"
                  label="E-Mail"
                  placeholder="john.doe@mail.com"
                  type="email"
                  prepend-icon="mdi-email"
              ></v-text-field>
              <v-text-field
                  name="password"
                  label="Passwort"
                  type="password"
                  prepend-icon="mdi-lock"
              ></v-text-field>
              <v-row>
                <v-col>
                  <v-text-field
                      name="firstName"
                      label="Vorname"
                      type="text"
                      prepend-icon="mdi-card-account-details"
                  ></v-text-field>
                </v-col>
                <v-col>
                  <v-text-field
                      name="lastName"
                      label="Nachname"
                      type="text"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
          <v-divider></v-divider>
          <v-card-actions>
            <v-btn to="/">Registrieren</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import {defineComponent} from 'vue'

/**
 * View to allow users to register. Not in use.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "RegisterView",
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