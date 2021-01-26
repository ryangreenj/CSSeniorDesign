import { FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";

export const PasswordMatchValidator1: ValidatorFn = (formGroup: FormGroup): ValidationErrors | null => {
  if (formGroup.get('password1').value === formGroup.get('password2').value)
    return null;
  else
    return {passwordMismatch: true};
};


export const PasswordMatchValidator2: ValidatorFn = (formGroup: FormGroup): ValidationErrors | null => {
  if (formGroup.get('password3').value === formGroup.get('password4').value)
    return null;
  else
    return {passwordMismatch: true};
};