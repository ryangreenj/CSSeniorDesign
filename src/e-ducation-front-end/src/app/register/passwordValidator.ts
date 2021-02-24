import { FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";

export const PasswordMatchValidator: ValidatorFn = (formGroup: FormGroup): ValidationErrors | null => {
  if (formGroup.get('password1').value === formGroup.get('password2').value)
    return null;
  else
    return {passwordMismatch: true};
};