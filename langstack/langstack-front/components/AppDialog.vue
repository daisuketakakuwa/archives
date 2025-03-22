<template>
  <v-dialog v-model="showDialog" :max-width="maxWidth" :persistent="persistent">
    <v-card>
      <v-toolbar class="text-h6" :color="headerColor" dark height="45px">
        {{ headerTitle }}
      </v-toolbar>
      <v-card-text class="px-6 pt-5 secondary--text text-h6"
        ><slot
      /></v-card-text>
      <v-card-actions class="pa-2">
        <v-spacer />
        <v-btn v-if="!hideOk" id="ok" :color="okColor" @click="ok">{{
          okTitle
        }}</v-btn>
        <v-btn v-if="!hideCancel" id="cancel" class="ml-2" @click="cancel">{{
          cancelTitle
        }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import { Component, Vue, Prop, PropSync } from "nuxt-property-decorator";
@Component
export default class AppDialog extends Vue {
  @PropSync("dialog", { type: Boolean, default: false })
  showDialog!: boolean;
  @Prop({ type: String || Number, default: null })
  maxWidth!: string | number;
  @Prop({ type: String, default: "" })
  headerTitle!: string;
  @Prop({ type: String, default: "primary" })
  headerColor!: string;
  @Prop({ type: String, default: "OK" })
  okTitle!: string;
  @Prop({ type: String, default: "primary" })
  okColor!: string;
  @Prop({ type: Boolean, default: false })
  hideOk!: boolean;
  @Prop({ type: Boolean, default: true })
  hideOkEvent!: boolean;
  @Prop({ type: String, default: "キャンセル" })
  cancelTitle!: string;
  @Prop({ type: Boolean, default: false })
  hideCancel!: boolean;
  @Prop({ type: Boolean, default: true })
  hideCancelEvent!: boolean;
  @Prop({ type: Boolean, default: false })
  persistent!: boolean;
  ok() {
    if (this.hideOkEvent) {
      this.showDialog = false;
    }
    this.$emit("ok");
  }
  cancel() {
    if (this.hideCancelEvent) {
      this.showDialog = false;
    }
    this.$emit("cancel");
  }
}
</script>